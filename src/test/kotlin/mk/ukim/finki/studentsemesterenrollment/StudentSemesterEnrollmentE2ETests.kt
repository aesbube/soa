package mk.ukim.finki.studentsemesterenrollment

import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.restassured.RestAssured
import io.restassured.config.LogConfig
import io.restassured.http.ContentType
import mk.ukim.finki.studentsemesterenrollment.aggregateSnapshot.SemesterSnapshot
import mk.ukim.finki.studentsemesterenrollment.aggregateSnapshot.SubjectAggregateSnapshot
import mk.ukim.finki.studentsemesterenrollment.commands.CreateStudentRecordCommand
import mk.ukim.finki.studentsemesterenrollment.config.loggerFor
import mk.ukim.finki.studentsemesterenrollment.config.Constants
import mk.ukim.finki.studentsemesterenrollment.events.CreateSubjectEvent
import mk.ukim.finki.studentsemesterenrollment.events.SemesterCreatedEvent
import mk.ukim.finki.studentsemesterenrollment.handlers.EventMessagingEventHandler
import mk.ukim.finki.studentsemesterenrollment.handlers.SemesterStateUpdatedEvent
import mk.ukim.finki.studentsemesterenrollment.handlers.StudentCreatedEvent
import mk.ukim.finki.studentsemesterenrollment.kafka.KafkaProducer
import mk.ukim.finki.studentsemesterenrollment.model.StudentRecord
import mk.ukim.finki.studentsemesterenrollment.model.StudentSemesterEnrollment
import mk.ukim.finki.studentsemesterenrollment.model.SubjectSlot
import mk.ukim.finki.studentsemesterenrollment.model.dto.SubjectEventData
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SemesterSnapshotRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.StudentRecordJpaRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.StudentSemesterEnrollmentJpaRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SubjectJpaRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SubjectSlotRepository
import mk.ukim.finki.studentsemesterenrollment.valueObjects.ClassesPerWeek
import mk.ukim.finki.studentsemesterenrollment.valueObjects.CycleSemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.ECTSCredits
import mk.ukim.finki.studentsemesterenrollment.valueObjects.GPA
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterState
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterType
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudyCycle
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudyProgram
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudyYear
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectAbbreviation
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectName
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectSlotStatus
import org.axonframework.commandhandling.gateway.CommandGateway
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

    @Autowired
    private lateinit var semesterSnapshotRepository: SemesterSnapshotRepository

    @Autowired
    private lateinit var studentRecordRepository: StudentRecordJpaRepository

    @Autowired
    private lateinit var subjectSlotRepository: SubjectSlotRepository

    @Autowired
    private lateinit var commandGateway: CommandGateway

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
        // Directly save subjects instead of publishing events
        Constants.subjects.forEach { subjectData ->
            val subject = SubjectAggregateSnapshot(
                id = SubjectCode(subjectData.id),
                name = SubjectName(subjectData.name),
                abbreviation = SubjectAbbreviation(subjectData.abbreviation),
                semesterCode = SemesterId(subjectData.semesterCode),
                ects = ECTSCredits(subjectData.ects),
                semester = SemesterType.valueOf(subjectData.semester),
                classesPerWeek = ClassesPerWeek(
                    lectures = subjectData.classesPerWeek.lectures,
                    auditoriumClasses = subjectData.classesPerWeek.auditoriumClasses,
                    labClasses = subjectData.classesPerWeek.labClasses
                )
            )
            subjectSnapshotRepository.save(subject)
        }

        subjectSnapshotRepository.flush() // Ensure it's committed

        // Directly save semester
        val semester = SemesterSnapshot(
            id = SemesterId("2021-22-W"),
            cycleSemesterId = CycleSemesterId(SemesterId("2021-22-W"), StudyCycle.UNDERGRADUATE),
            state = SemesterState.STUDENTS_ENROLLMENT,
            enrollmentStartDate = ZonedDateTime.now().toLocalDateTime(),
            enrollmentEndDate = ZonedDateTime.now().plusDays(21).toLocalDateTime()
        )
        semesterSnapshotRepository.save(semester)
        semesterSnapshotRepository.flush()

        createStudentRecordManually("216049")
    }

    private fun createStudentRecordManually(studentId: String): StudentRecord {
        val studentRecord = StudentRecord().apply {
            setPrivateField("id", StudentId(studentId))
            setPrivateField("ects", ECTSCredits(0))
            setPrivateField("studyProgram", StudyProgram.Type.COMPUTER_SCIENCE.toStudyProgram())
            setPrivateField("gpa", GPA(5.0))
            setPrivateField("enrollmentYear", StudyYear("2021-22"))
            setPrivateField("winterSemesterNumber", 0)
            setPrivateField("summerSemesterNumber", 0)
            setPrivateField("createdAt", LocalDateTime.now().withNano(0))
            setPrivateField("subjectSlots", mutableListOf<SubjectSlot>())
            setPrivateField("passedSubjects", mutableListOf<SubjectAggregateSnapshot>())
        }

        val savedStudentRecord = studentRecordRepository.save(studentRecord)
        studentRecordRepository.flush()

        val subjects = subjectSnapshotRepository.findAll().take(10).map { it.id }
        val subjectSlots = subjects.map { code ->
            SubjectSlot(
                subjectId = code,
                electiveSubjectGroup = null,
                status = SubjectSlotStatus.NOT_ENROLLED,
                studentId = StudentId(studentId),
                exam = null
            )
        }

        subjectSlotRepository.saveAll(subjectSlots)
        subjectSlotRepository.flush()

        return savedStudentRecord
    }

    // Helper extension function
    private fun Any.setPrivateField(fieldName: String, value: Any?) {
        val field = this::class.java.getDeclaredField(fieldName)
        field.isAccessible = true
        field.set(this, value)
    }

    @Test
    fun `should start enrollment for student successfully`() {
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

        val allStudentEnrollments = studentSemesterEnrollmentJpaRepository.findAll()
        assertEquals(1, allStudentEnrollments.size, "No enrollments found")
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