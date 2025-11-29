package mk.ukim.finki.studentsemesterenrollment

import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.restassured.RestAssured
import io.restassured.config.LogConfig
import io.restassured.http.ContentType
import jakarta.persistence.EntityManager
import mk.ukim.finki.studentsemesterenrollment.aggregateSnapshot.SemesterSnapshot
import mk.ukim.finki.studentsemesterenrollment.aggregateSnapshot.SubjectAggregateSnapshot
import mk.ukim.finki.studentsemesterenrollment.commands.CreateStudentRecordCommand
import mk.ukim.finki.studentsemesterenrollment.config.Constants
import mk.ukim.finki.studentsemesterenrollment.config.loggerFor
import mk.ukim.finki.studentsemesterenrollment.handlers.EventMessagingEventHandler
import mk.ukim.finki.studentsemesterenrollment.kafka.KafkaProducer
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SemesterSnapshotRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.StudentRecordJpaRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.StudentSemesterEnrollmentJpaRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.StudentSubjectEnrollmentJpaRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SubjectJpaRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SubjectSlotRepository
import mk.ukim.finki.studentsemesterenrollment.service.impl.StudentRecordCommandService
import mk.ukim.finki.studentsemesterenrollment.valueObjects.ClassesPerWeek
import mk.ukim.finki.studentsemesterenrollment.valueObjects.CycleSemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.ECTSCredits
import mk.ukim.finki.studentsemesterenrollment.valueObjects.EnrollmentStatus
import mk.ukim.finki.studentsemesterenrollment.valueObjects.Grade
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterState
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterType
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudyCycle
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudyProgram
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectAbbreviation
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectName
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventhandling.gateway.EventGateway
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Order
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
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.ZonedDateTime
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
@Transactional
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
    private lateinit var studentRecordCommandService: StudentRecordCommandService

    @Autowired
    private lateinit var subjectSlotRepository: SubjectSlotRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Autowired
    private lateinit var commandGateway: CommandGateway

    @LocalServerPort
    private val port: Int = 0

    @BeforeAll
    fun beforeAll() {
        Constants.subjects.map { subjectData ->
            SubjectAggregateSnapshot(
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
        }.let {
            subjectSnapshotRepository.saveAllAndFlush(it)
        }

        val semester = SemesterSnapshot(
            id = SemesterId("2021-22-W"),
            cycleSemesterId = CycleSemesterId(SemesterId("2021-22-W"), StudyCycle.UNDERGRADUATE),
            state = SemesterState.STUDENTS_ENROLLMENT,
            enrollmentStartDate = ZonedDateTime.now().toLocalDateTime(),
            enrollmentEndDate = ZonedDateTime.now().plusDays(21).toLocalDateTime()
        )
        semesterSnapshotRepository.saveAndFlush(semester)
        val semester2 = SemesterSnapshot(
            id = SemesterId("2021-22-S"),
            cycleSemesterId = CycleSemesterId(SemesterId("2021-22-S"), StudyCycle.UNDERGRADUATE),
            state = SemesterState.STUDENTS_ENROLLMENT,
            enrollmentStartDate = ZonedDateTime.now().toLocalDateTime(),
            enrollmentEndDate = ZonedDateTime.now().plusDays(21).toLocalDateTime()
        )
        semesterSnapshotRepository.saveAndFlush(semester2)

        val semester3 = SemesterSnapshot(
            id = SemesterId("2022-23-W"),
            cycleSemesterId = CycleSemesterId(SemesterId("2022-23-W"), StudyCycle.UNDERGRADUATE),
            state = SemesterState.STUDENTS_ENROLLMENT,
            enrollmentStartDate = ZonedDateTime.now().toLocalDateTime(),
            enrollmentEndDate = ZonedDateTime.now().plusDays(21).toLocalDateTime()
        )
        semesterSnapshotRepository.saveAndFlush(semester3)

        val studentRecord = commandGateway.sendAndWait<StudentId>(CreateStudentRecordCommand(
            studyProgram = StudyProgram.Type.COMPUTER_SCIENCE.toStudyProgram(),
            id = StudentId("216049"),
            ects = ECTSCredits(0)
        ))
    }

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

//    @AfterEach
//    fun cleanup() {
//        unmockkStatic(LocalDateTime::class)
//    }

    @Order(1)
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
        entityManager.clear()
        val updatedEnrollment = studentSemesterEnrollmentJpaRepository.findByIdOrNull(StudentSemesterEnrollmentId("2021-22-W-1-216049"))

        assertEquals(EnrollmentStatus.STUDENT_CONFIRMED, updatedEnrollment?.getStatus())
    }

    @Test
    @Order(2)
    fun `enroll student in second semester`() {
//        every { LocalDateTime.now() } returns LocalDateTime.parse("2021-09-30T00:08:00")

        val enrollmentRequest = """{
            "studentIndex": "216049",
            "cycleId": "UNDERGRADUATE",
            "semesterId": "2021-22-S"
        }"""

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(enrollmentRequest)
            .post("/student-semester-enrollment")
            .then().log().all()
            .statusCode(200)
            .body(notNullValue())

        val enrollment = studentSemesterEnrollmentJpaRepository.findByIdOrNull(StudentSemesterEnrollmentId("2021-22-S-1-216049"))

        assertNotNull(enrollment, "Enrollment [2021-22-W-1-216049] not found")
        assertEquals(enrollment.getStatus(), EnrollmentStatus.INITIATED)

        val enrollmentId = enrollment.getId()
        val secondSemesterSubjects = listOf(
            "F18L1S032",
            "F18L1S016",
            "F18L1S003",
            "F18L1S034",
            "F18L1S146"//elective
        )
        secondSemesterSubjects.forEach {
            enrollInSubject(enrollmentId.id, it)
        }

        val subjectEnrollments = studentSubjectEnrollmentJpaRepository.findAll().filter {
            it.getId().subjectCode().value in secondSemesterSubjects
        }

        assertEquals(secondSemesterSubjects.size, subjectEnrollments.size, "There should be 5 enrolled subjects")

        RestAssured.given()
            .contentType(ContentType.JSON)
            .put("/student-semester-enrollment/${enrollmentId.id}/confirm")
            .then().log().all()
            .statusCode(200)
            .body(notNullValue())

        Thread.sleep(2000)
        entityManager.clear()
        val updatedEnrollment = studentSemesterEnrollmentJpaRepository.findByIdOrNull(StudentSemesterEnrollmentId("2021-22-S-1-216049"))

        assertEquals(EnrollmentStatus.STUDENT_CONFIRMED, updatedEnrollment?.getStatus())
    }

    @Test
    @Order(3)
    fun `enroll student in third semester, with failed subjects`() {
//        every { LocalDateTime.now() } returns LocalDateTime.parse("2021-09-30T00:08:00")

//        studentRecordRepository.findByIdOrNull(StudentId("216049"))?.getSubjectSlots()?.map {
//            studentRecordCommandService.subjectPassed(
//                studentId = it.studentId().index,
//                subjectCode = it.subjectId().value,
//                grade = Grade(10)
//            )
//        }

        val enrollmentRequest = """{
            "studentIndex": "216049",
            "cycleId": "UNDERGRADUATE",
            "semesterId": "2022-23-W"
        }"""

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(enrollmentRequest)
            .post("/student-semester-enrollment")
            .then().log().all()
            .statusCode(200)
            .body(notNullValue())

        val enrollment = studentSemesterEnrollmentJpaRepository.findByIdOrNull(StudentSemesterEnrollmentId("2022-23-W-1-216049"))

        assertNotNull(enrollment, "Enrollment [2022-23-W-1-216049] not found")
        assertEquals(enrollment.getStatus(), EnrollmentStatus.INITIATED)
        assertEquals(enrollment.getEnrolledSubjects().size, 5, "There should be 5 enrolled subjects, from the previous semester")
    }

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