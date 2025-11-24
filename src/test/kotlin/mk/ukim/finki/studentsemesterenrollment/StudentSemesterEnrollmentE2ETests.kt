package mk.ukim.finki.studentsemesterenrollment

import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.restassured.RestAssured
import io.restassured.config.LogConfig
import io.restassured.http.ContentType
import mk.ukim.finki.studentsemesterenrollment.config.loggerFor
import mk.ukim.finki.studentsemesterenrollment.handlers.EventMessagingEventHandler
import mk.ukim.finki.studentsemesterenrollment.kafka.KafkaProducer
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SemesterSnapshotRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.StudentRecordJpaRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.StudentSemesterEnrollmentJpaRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.StudentSubjectEnrollmentJpaRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SubjectJpaRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SubjectSlotRepository
import mk.ukim.finki.studentsemesterenrollment.valueObjects.EnrollmentStatus
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId
import org.axonframework.eventhandling.gateway.EventGateway
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

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
    private lateinit var studentSubjectEnrollmentJpaRepository: StudentSubjectEnrollmentJpaRepository

    @Autowired
    private lateinit var eventGateway: EventGateway

    @Autowired
    private lateinit var semesterSnapshotRepository: SemesterSnapshotRepository

    @Autowired
    private lateinit var studentRecordRepository: StudentRecordJpaRepository

    @Autowired
    private lateinit var subjectSlotRepository: SubjectSlotRepository

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
//        initializeTestData()
    }

    @AfterEach
    fun cleanup() {
        unmockkStatic(LocalDateTime::class)
    }

    @Test
    fun `enroll student in first semester`() {
//        every { LocalDateTime.now() } returns LocalDateTime.parse("2021-09-30T00:08:00")

        println("Subjects in DB: ${subjectSnapshotRepository.count()}")
        println("Subjects in DB: ${studentRecordRepository.count()}")
        println("Semesters in DB: ${semesterSnapshotRepository.count()}")
        println("First 3 subjects: ${subjectSnapshotRepository.findAll().take(3).map { it.id }}")

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

        val enrollment = studentSemesterEnrollmentJpaRepository.findByIdOrNull(StudentSemesterEnrollmentId("2021-22-W-1-216049"))

        assertNotNull(enrollment, "Enrollment [2021-22-W-1-216049] not found")
        assertEquals(enrollment.getStatus(), EnrollmentStatus.INITIATED)

        val enrollmentId = enrollment.getId()
        val firstYearSubjects = listOf(
            "F18L1W020",
            "F18L1W033",
            "F18L1W007",
            "F18L1W031",
            "F18L1W018",
        )
        firstYearSubjects.forEach {
            enrollInSubject(enrollmentId.id, it)
        }

        val subjectEnrollments = studentSubjectEnrollmentJpaRepository.findAll().filter {
            it.getId().subjectCode().value in firstYearSubjects
        }

        assertEquals(firstYearSubjects.size, subjectEnrollments.size, "There should be 5 enrolled subjects")

        RestAssured.given()
            .contentType(ContentType.JSON)
            .put("/student-semester-enrollment/${enrollmentId.id}/confirm")
            .then().log().all()
            .statusCode(200)
            .body(notNullValue())

        Thread.sleep(2000)
        val updatedEnrollment = studentSemesterEnrollmentJpaRepository.findByIdOrNull(StudentSemesterEnrollmentId("2021-22-W-1-216049"))

        assertEquals(EnrollmentStatus.STUDENT_CONFIRMED, updatedEnrollment?.getStatus())
    }

//    @Test
//    fun `enroll student in second semester`() {
////        every { LocalDateTime.now() } returns LocalDateTime.parse("2021-09-30T00:08:00")
//
//        val enrollmentRequest = """{
//            "studentIndex": "216049",
//            "cycleId": "UNDERGRADUATE",
//            "semesterId": "2021-22-S"
//        }"""
//
//        RestAssured.given()
//            .contentType(ContentType.JSON)
//            .body(enrollmentRequest)
//            .post("/student-semester-enrollment")
//            .then().log().all()
//            .statusCode(200)
//            .body(notNullValue())
//
//        val enrollment = studentSemesterEnrollmentJpaRepository.findByIdOrNull(StudentSemesterEnrollmentId("2021-22-S-1-216049"))
//
//        assertNotNull(enrollment, "Enrollment [2021-22-W-1-216049] not found")
//        assertEquals(enrollment.getStatus(), EnrollmentStatus.INITIATED)
//
//        val enrollmentId = enrollment.getId()
//        val secondSemesterSubjects = listOf(
//            "F18L1S032",
//            "F18L1S016",
//            "F18L1S003",
//            "F18L1S034",
//            "F18L1S146"//elective
//        )
//        secondSemesterSubjects.forEach {
//            enrollInSubject(enrollmentId.id, it)
//        }
//
//        val subjectEnrollments = studentSubjectEnrollmentJpaRepository.findAll().filter {
//            it.getId().subjectCode().value in secondSemesterSubjects
//        }
//
//        assertEquals(secondSemesterSubjects.size, subjectEnrollments.size, "There should be 5 enrolled subjects")
//
//        RestAssured.given()
//            .contentType(ContentType.JSON)
//            .put("/student-semester-enrollment/${enrollmentId.id}/confirm")
//            .then().log().all()
//            .statusCode(200)
//            .body(notNullValue())
//
//        val updatedEnrollment = studentSemesterEnrollmentJpaRepository.findByIdOrNull(StudentSemesterEnrollmentId("2021-22-S-1-216049"))
//
//        assertEquals(EnrollmentStatus.STUDENT_CONFIRMED, updatedEnrollment?.getStatus())
//    }

//    @Test
//    fun `enroll student in third semester, with failed subjects`() {
////        every { LocalDateTime.now() } returns LocalDateTime.parse("2021-09-30T00:08:00")
//
//        studentRecordRepository.findByIdOrNull(StudentId("216049"))?.let {
//            it.
//        }
//
//        val enrollmentRequest = """{
//            "studentIndex": "216049",
//            "cycleId": "UNDERGRADUATE",
//            "semesterId": "2022-23-W"
//        }"""
//
//        RestAssured.given()
//            .contentType(ContentType.JSON)
//            .body(enrollmentRequest)
//            .post("/student-semester-enrollment")
//            .then().log().all()
//            .statusCode(200)
//            .body(notNullValue())
//
//        val enrollment = studentSemesterEnrollmentJpaRepository.findByIdOrNull(StudentSemesterEnrollmentId("2022-23-W-1-216049"))
//
//        assertNotNull(enrollment, "Enrollment [2022-23-W-1-216049] not found")
//        assertEquals(enrollment.getStatus(), EnrollmentStatus.INITIATED)
//
//        val enrollmentId = enrollment.getId()
//        val secondSemesterSubjects = listOf(
//            "F18L1S032",
//            "F18L1S016",
//            "F18L1S003",
//            "F18L1S034",
//            "F18L1S146"//elective
//        )
//        secondSemesterSubjects.forEach {
//            enrollInSubject(enrollmentId.id, it)
//        }
//
//        val subjectEnrollments = studentSubjectEnrollmentJpaRepository.findAll().filter {
//            it.getId().subjectCode().value in secondSemesterSubjects
//        }
//
//        assertEquals(secondSemesterSubjects.size, subjectEnrollments.size, "There should be 5 enrolled subjects")
//
//        RestAssured.given()
//            .contentType(ContentType.JSON)
//            .put("/student-semester-enrollment/${enrollmentId.id}/confirm")
//            .then().log().all()
//            .statusCode(200)
//            .body(notNullValue())
//
//        val updatedEnrollment = studentSemesterEnrollmentJpaRepository.findByIdOrNull(StudentSemesterEnrollmentId("2022-23-W-1-216049"))
//
//        assertEquals(EnrollmentStatus.STUDENT_CONFIRMED, updatedEnrollment?.getStatus())
//    }

    private fun enrollInSubject(enrollmentId: String, subjectId: String) {
        RestAssured.given()
            .contentType(ContentType.JSON)
            .put("/student-semester-enrollment/$enrollmentId/$subjectId")
            .then().log().all()
            .statusCode(200)
            .body(notNullValue())
    }
//
//    @Test
//    fun `complete enrollment workflow - start, enroll subjects, validate, pay, confirm`() {
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
//            .then().log().all()
//            .statusCode(200)
//            .extract()
//            .asString()
//
//        val subjects = listOf("F18L3W141", "F18L3W142", "F18L3W143")
//        subjects.forEach { subjectCode ->
//            RestAssured.given()
//                .contentType(ContentType.JSON)
//                .put("/student-semester-enrollment/$enrollmentId/$subjectCode")
//                .then().log().all()
//                .statusCode(200)
//        }
//
//        RestAssured.given()
//            .contentType(ContentType.JSON)
//            .put("/student-semester-enrollment/$enrollmentId/validate")
//            .then().log().all()
//            .statusCode(200)
//
//        RestAssured.given()
//            .contentType(ContentType.JSON)
//            .put("/student-semester-enrollment/$enrollmentId/update-payment")
//            .then().log().all()
//            .statusCode(200)
//
//        RestAssured.given()
//            .contentType(ContentType.JSON)
//            .put("/student-semester-enrollment/$enrollmentId/confirm")
//            .then().log().all()
//            .statusCode(200)
//    }
}