package mk.ukim.finki.studentsemesterenrollment

import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.restassured.RestAssured
import io.restassured.config.LogConfig
import io.restassured.http.ContentType
import mk.ukim.finki.studentsemesterenrollment.aggregateSnapshot.SubjectAggregateSnapshot
import mk.ukim.finki.studentsemesterenrollment.config.loggerFor
import mk.ukim.finki.studentsemesterenrollment.config.Constants
import mk.ukim.finki.studentsemesterenrollment.events.CreateSubjectEvent
import mk.ukim.finki.studentsemesterenrollment.events.SemesterCreatedEvent
import mk.ukim.finki.studentsemesterenrollment.handlers.EventMessagingEventHandler
import mk.ukim.finki.studentsemesterenrollment.handlers.SemesterStateUpdatedEvent
import mk.ukim.finki.studentsemesterenrollment.handlers.StudentCreatedEvent
import mk.ukim.finki.studentsemesterenrollment.kafka.KafkaProducer
import mk.ukim.finki.studentsemesterenrollment.model.StudentSemesterEnrollment
import mk.ukim.finki.studentsemesterenrollment.model.dto.SubjectEventData
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SemesterSnapshotRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.StudentSemesterEnrollmentJpaRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SubjectJpaRepository
import mk.ukim.finki.studentsemesterenrollment.valueObjects.ClassesPerWeek
import mk.ukim.finki.studentsemesterenrollment.valueObjects.ECTSCredits
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterState
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterType
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudyProgram
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectAbbreviation
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectName
import org.axonframework.eventhandling.gateway.EventGateway
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.ZonedDateTime
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(
    properties = [
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration",
        "axon.axonserver.enabled=false",
        "resilience4j.enabled=false",
        "spring.kafka.bootstrap-servers=",
        "spring.kafka.consumer.group-id=",
        "spring.kafka.listener.missing-topics-fatal=false"
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class StudentSemesterEnrollmentE2ETests {

    private val logger = loggerFor<StudentSemesterEnrollmentE2ETests>()

    @MockBean
    private lateinit var kafkaProducer: KafkaProducer

    @MockBean
    private lateinit var eventMessagingEventHandler: EventMessagingEventHandler

    @Autowired
    private lateinit var subjectSnapshotRepository: SubjectJpaRepository

    @Autowired
    private lateinit var studentSemesterEnrollmentJpaRepository: StudentSemesterEnrollmentJpaRepository

    @Autowired
    private lateinit var eventGateway: EventGateway

    @LocalServerPort
    private val port: Int = 0

    @BeforeEach
    fun setup() {
        RestAssured.port = port
        RestAssured.baseURI = "http://localhost"
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
        RestAssured.config = RestAssured.config()
            .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails().enablePrettyPrinting(true))

        mockkStatic(LocalDateTime::class)

        // Move initialization HERE (runs before each test)
        initializeTestData()
    }

    @AfterEach
    fun cleanup() {
        unmockkStatic(LocalDateTime::class)
    }

    private fun initializeTestData() {
        // create F18 subjects from finki common-model
        Constants.subjects.forEach {
            eventGateway.publish(CreateSubjectEvent(
                subjectData = it,
                id = SubjectCode(it.id)
            ))
        }

        // init first semester
        eventGateway.publish(SemesterCreatedEvent(
            semesterId = SemesterId("2021-22-W"),
            startDate = ZonedDateTime.now(),
            endDate = ZonedDateTime.now(),
            enrollmentEndDate = ZonedDateTime.now(),
            enrollmentStartDate = ZonedDateTime.now(),
        ))

        // simulate creation of student record
        eventGateway.publish(StudentCreatedEvent(
            studyProgram = StudyProgram.Type.COMPUTER_SCIENCE.toStudyProgram(),
            studentId = "216049"
        ))

        eventGateway.publish(SemesterStateUpdatedEvent(
            semesterId = SemesterId("2021-22-W"),
            state = SemesterState.STUDENTS_ENROLLMENT
        ))
    }

    @Test
    @Transactional
    fun `should start enrollment for student successfully`() {
        every { LocalDateTime.now() } returns LocalDateTime.parse("2021-09-30T00:08:00")

        val enrollmentRequest = """{
            "studentIndex": "216049",
            "cycleId": "UNDERGRADUATE",
            "semesterId": "2021-22-W"
        }"""

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(enrollmentRequest)
            .post("/student-semester-enrollment")
            .then().log().all()
            .statusCode(200)
            .body(notNullValue())

        val allStudentEnrollments = studentSemesterEnrollmentJpaRepository.findAll()
        assertEquals(1, allStudentEnrollments.size, "No enrollments found")
    }
}

//    @Test
//    fun `complete enrollment workflow - start, enroll subjects, validate, pay, confirm`() {
//        // Start enrollment
//        val enrollmentRequest = """{
//            "studentIndex": "216040",
//            "cycleId": "UNDERGRADUATE",
//            "semesterId": "2025-26-W"
//        }"""
//
//        val enrollmentId = RestAssured.given()
//            .contentType(ContentType.JSON)
//            .body(enrollmentRequest)
//            .post("/student-semester-enrollment")
//            .then().log().all()  // Log response details
//            .statusCode(200)
//            .extract()
//            .asString()
//
//        logger.debug("AAAAAAAAA ${enrollmentId}")
//        println("AAAAAAAAA ${enrollmentId}")
//
//        // Enroll on subjects
//        val subjects = listOf("F18L3W141", "F18L3W142", "F18L3W143")
//        subjects.forEach { subjectCode ->
//            RestAssured.given()
//                .contentType(ContentType.JSON)
//                .put("/student-semester-enrollment/$enrollmentId/$subjectCode")
//                .then().log().all()  // Log response details
//                .statusCode(200)
//        }
//
//        // Validate enrollment
//        RestAssured.given()
//            .contentType(ContentType.JSON)
//            .put("/student-semester-enrollment/$enrollmentId/validate")
//            .then().log().all()  // Log response details
//            .statusCode(200)
//
//        // Update payment
//        RestAssured.given()
//            .contentType(ContentType.JSON)
//            .put("/student-semester-enrollment/$enrollmentId/update-payment")
//            .then().log().all()  // Log response details
//            .statusCode(200)
//
//        // Confirm enrollment
//        RestAssured.given()
//            .contentType(ContentType.JSON)
//            .put("/student-semester-enrollment/$enrollmentId/confirm")
//            .then().log().all()  // Log response details
//            .statusCode(200)
//    }

// }