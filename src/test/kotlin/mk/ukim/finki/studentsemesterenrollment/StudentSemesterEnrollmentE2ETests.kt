package mk.ukim.finki.studentsemesterenrollment

import io.mockk.mockkStatic
import io.restassured.RestAssured
import io.restassured.config.LogConfig
import io.restassured.http.ContentType
import jakarta.persistence.EntityManager
import mk.ukim.finki.studentsemesterenrollment.aggregateSnapshot.SemesterSnapshot
import mk.ukim.finki.studentsemesterenrollment.aggregateSnapshot.SubjectAggregateSnapshot
import mk.ukim.finki.studentsemesterenrollment.commands.CreateStudentRecordCommand
import mk.ukim.finki.studentsemesterenrollment.config.Constants
import mk.ukim.finki.studentsemesterenrollment.handlers.EventMessagingEventHandler
import mk.ukim.finki.studentsemesterenrollment.kafka.KafkaProducer
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SemesterSnapshotRepository
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
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSubjectEnrollmentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudyCycle
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudyProgram
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectAbbreviation
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectName
import org.axonframework.commandhandling.gateway.CommandGateway
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.Profile
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionTemplate
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
@Profile("test")
class StudentSemesterEnrollmentE2ETests {
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
    private lateinit var subjectSlotRepository: SubjectSlotRepository

    @Autowired
    private lateinit var semesterSnapshotRepository: SemesterSnapshotRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @Autowired
    private lateinit var commandGateway: CommandGateway

    @Autowired
    private lateinit var studentRecordCommandService: StudentRecordCommandService

    @Autowired
    private lateinit var transactionTemplate: TransactionTemplate

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

        semesters.forEach {
            val semester = SemesterSnapshot(
                id = it,
                cycleSemesterId = CycleSemesterId(it, StudyCycle.UNDERGRADUATE),
                state = SemesterState.STUDENTS_ENROLLMENT,
                enrollmentStartDate = ZonedDateTime.now().toLocalDateTime(),
                enrollmentEndDate = ZonedDateTime.now().plusDays(21).toLocalDateTime()
            )
            semesterSnapshotRepository.saveAndFlush(semester)
        }

        students.forEach {
            commandGateway.sendAndWait<StudentId>(
                CreateStudentRecordCommand(
                    studyProgram = StudyProgram.Type.COMPUTER_SCIENCE.toStudyProgram(),
                    id = it,
                    ects = ECTSCredits(0)
                )
            )
        }
    }

    @BeforeEach
    fun setup() {
        RestAssured.port = port
        RestAssured.baseURI = "http://localhost"
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
        RestAssured.config = RestAssured.config()
            .logConfig(
                LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails().enablePrettyPrinting(true)
            )

        mockkStatic(LocalDateTime::class)
    }


    companion object {
        val students = listOf(
            StudentId("216049"),
            StudentId("216173"),
            StudentId("216174"),
            StudentId("216175"),
            StudentId("216176"),
        )
        val firstSemesterSubjects = listOf(
            "F18L1W020",
            "F18L1W033",
            "F18L1W007",
            "F18L1W031",
            "F18L1W018",
        )

        val secondSemesterSubjects = listOf(
            "F18L1S003",
            "F18L1S032",
            "F18L1S016",
            "F18L1S034",
            "F18L1S146"
        )

        val thirdSemesterSubjects = listOf(
            "F18L2W001",
            "F18L2W006",
            "F18L2W109",
            "F18L2W014",
            "F18L2W140"
        )

        val fourthSemesterSubjects = listOf(
            "F18L2S030",
            "F18L2S017",
            "F18L2S029",
            "F18L2S110",
            "F18L2S114"
        )

        val fifthSemesterSubjects = listOf(
            "F18L3W035",
            "F18L3W037",
            "F18L3W008",
            "F18L3W024",
            "F18L3W004"
        )

        val sixthSemesterSubjects = listOf(
            "F18L3S010",
            "F18L3S118",
            "F18L3S036",
            "F18L3S087",
            "F18L3S039"
        )

        val seventhSemesterSubjects = listOf(
            "F18L3W075",
            "F18L3W038",
            "F18L3W021",
            "F18L3W074",
            "F18L3W103"
        )

        val eighthSemesterSubjects = listOf(
            "F18L3S159",
            "F18L3S155",
            "F18L3S022",
            "F18L3S086",
            "F18L3S168"
        )

        val semesters = listOf(
            SemesterId("2021-22-W"),
            SemesterId("2021-22-S"),
            SemesterId("2022-23-W"),
            SemesterId("2022-23-S"),
            SemesterId("2023-24-W"),
            SemesterId("2023-24-S"),
            SemesterId("2024-25-W"),
            SemesterId("2024-25-S"),
        )

        val subjects = listOf(
            firstSemesterSubjects,
            secondSemesterSubjects,
            thirdSemesterSubjects,
            fourthSemesterSubjects,
            fifthSemesterSubjects,
            sixthSemesterSubjects,
            seventhSemesterSubjects,
            eighthSemesterSubjects,
        )
        val semesterSubjects = semesters.mapIndexed { index, semester -> semester to subjects[index] }.toMap()
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    fun `flawless student`() {
        val studentId = StudentId("216173")

        semesterSubjects.forEach { (semesterId, subjects) ->
            enrollInSemester(
                studentId = studentId,
                semesterId = semesterId,
                cycle = StudyCycle.UNDERGRADUATE,
                subjects = subjects
            )
            passSubjects(subjects.associateWith { Grade(10) }, studentId)
        }

    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    fun `enroll student in first semester`() {
        enrollInSemester(
            studentId = StudentId("216049"),
            semesterId = SemesterId("2021-22-W"),
            cycle = StudyCycle.UNDERGRADUATE,
            subjects = listOf(
                "F18L1W020",
                "F18L1W033",
                "F18L1W007",
                "F18L1W031",
                "F18L1W018",
            )
        )

        passSubjects(
            mapOf(
                "F18L1W020" to Grade(10),
                "F18L1W033" to Grade(10),
                "F18L1W007" to Grade(10),
            ), StudentId("216049")
        )

        enrollInSemester(
            studentId = StudentId("216049"),
            semesterId = SemesterId("2021-22-S"),
            cycle = StudyCycle.UNDERGRADUATE,
            subjects = listOf(
                "F18L1S032",
                "F18L1S016",
                "F18L1S003",
                "F18L1S034",
                "F18L1S146"//elective
            )
        )

        passSubjects(
            mapOf(
                "F18L1S032" to Grade(10),
                "F18L1S016" to Grade(10),
                "F18L1S003" to Grade(10),
                "F18L1S034" to Grade(10),
                "F18L1S146" to Grade(10)//elective
            ), StudentId("216049")
        )

        enrollInSemesterWithFailedSubjects(
            studentId = StudentId("216049"),
            semesterId = SemesterId("2022-23-W"),
            cycle = StudyCycle.UNDERGRADUATE,
            subjects = listOf(
                "F18L2W001",
                "F18L2W006",
                "F18L2W109",
            ),
            expectedFailedSubjects = listOf(
                "F18L1W031",
                "F18L1W018",
            )
        )
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    fun `enroll student with failed elective subject`() {
        enrollInSemester(
            studentId = StudentId("216174"),
            semesterId = SemesterId("2021-22-W"),
            cycle = StudyCycle.UNDERGRADUATE,
            subjects = listOf(
                "F18L1W020",
                "F18L1W033",
                "F18L1W007",
                "F18L1W031",
                "F18L1W018",
            )
        )

        passSubjects(
            mapOf(
                "F18L1W020" to Grade(10),
                "F18L1W033" to Grade(10),
                "F18L1W007" to Grade(10),
            ), StudentId("216174")
        )

        enrollInSemester(
            studentId = StudentId("216174"),
            semesterId = SemesterId("2021-22-S"),
            cycle = StudyCycle.UNDERGRADUATE,
            subjects = listOf(
                "F18L1S032",
                "F18L1S016",
                "F18L1S003",
                "F18L1S034",
                "F18L1S146"//elective
            )
        )

        passSubjects(
            mapOf(
                "F18L1S032" to Grade(10),
                "F18L1S016" to Grade(10),
                "F18L1S003" to Grade(10),
                "F18L1S034" to Grade(10),
            ), StudentId("216174")
        )

        enrollInSemesterWithFailedSubjects(
            studentId = StudentId("216174"),
            semesterId = SemesterId("2022-23-W"),
            cycle = StudyCycle.UNDERGRADUATE,
            subjects = listOf(
                "F18L2W001",
                "F18L2W006",
                "F18L2W109",
            ),
            expectedFailedSubjects = listOf(
                "F18L1W031",
                "F18L1W018",
            )
        )

        passSubjects(
            mapOf(
                "F18L2W001" to Grade(10),
                "F18L2W006" to Grade(10),
                "F18L2W109" to Grade(10),
                "F18L1W031" to Grade(10),
                "F18L1W018" to Grade(10),
            ), StudentId("216174")
        )

        enrollInSemesterWithFailedSubjects(
            studentId = StudentId("216174"),
            semesterId = SemesterId("2022-23-S"),
            cycle = StudyCycle.UNDERGRADUATE,
            subjects = listOf(
                "F18L2S030",
                "F18L2S017",
                "F18L2S029",
                "F18L2S110"
            ),
            expectedFailedSubjects = listOf()
        )
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    fun `enroll student with less than 21 credits`() {
        enrollInSemester(
            studentId = StudentId("216175"),
            semesterId = SemesterId("2021-22-W"),
            cycle = StudyCycle.UNDERGRADUATE,
            subjects = listOf(
                "F18L1W020",
                "F18L1W033",
                "F18L1W007"
            ),
            valid = false
        )
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    fun `enroll student with more than 36 credits`() {
        enrollInSemester(
            studentId = StudentId("216176"),
            semesterId = SemesterId("2021-22-W"),
            cycle = StudyCycle.UNDERGRADUATE,
            subjects = listOf(
                "F18L1W020",
                "F18L1W033",
                "F18L1W007",
                "F18L1W031",
                "F18L1W018",
                "F18L2W014",
                "F18L2W140"
            ),
            valid = false
        )
    }

    private fun enrollInSubject(enrollmentId: String, subjectId: String) {
        RestAssured.given()
            .contentType(ContentType.JSON)
            .put("/student-semester-enrollment/$enrollmentId/$subjectId")
            .then().log().all()
            .statusCode(200)
            .body(notNullValue())
    }

    private fun enrollInSemester(
        studentId: StudentId, cycle: StudyCycle, semesterId: SemesterId, subjects: List<String>, valid: Boolean = true
    ) {
        val enrollmentRequest = """{
            "studentIndex": "${studentId.index}",
            "cycleId": "${cycle.name}",
            "semesterId": "${semesterId.value}"
        }"""

        val enrollmentId = StudentSemesterEnrollmentId(CycleSemesterId(semesterId, cycle), studentId)

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(enrollmentRequest)
            .post("/student-semester-enrollment")
            .then().log().all()
            .statusCode(200)
            .body(notNullValue())

        val enrollment = studentSemesterEnrollmentJpaRepository.findByIdOrNull(enrollmentId)

        assertNotNull(enrollment, "Enrollment [${enrollmentId.id}] not found")
        assertEquals(enrollment.getStatus(), EnrollmentStatus.INITIATED)

        enrollStudentInSemesterSubjects(
            subjects = subjects,
            enrollmentId = enrollmentId,
            valid = valid
        )
    }

    fun enrollInSemesterWithFailedSubjects(
        studentId: StudentId, cycle: StudyCycle, semesterId: SemesterId, subjects: List<String>,
        expectedFailedSubjects: List<String>, valid: Boolean = true
    ) {
        val enrollmentRequest = """{
            "studentIndex": "${studentId.index}",
            "cycleId": "${cycle.name}",
            "semesterId": "${semesterId.value}"
        }"""

        val enrollmentId = StudentSemesterEnrollmentId(CycleSemesterId(semesterId, cycle), studentId)
        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(enrollmentRequest)
            .post("/student-semester-enrollment")
            .then().log().all()
            .statusCode(200)
            .body(notNullValue())

        val enrollment = studentSemesterEnrollmentJpaRepository.findByIdOrNull(enrollmentId)

        assertNotNull(enrollment, "Enrollment [${enrollmentId.id}] not found")
        assertEquals(enrollment.getStatus(), EnrollmentStatus.INITIATED)

        transactionTemplate.execute {
            assertEquals(
                expectedFailedSubjects.size, enrollment.getEnrolledSubjects().size,
                "There should be ${expectedFailedSubjects.size} enrolled subjects, from the previous semester"
            )
        }

        enrollStudentInSemesterSubjects(
            subjects = subjects,
            enrollmentId = enrollmentId,
            valid = valid
        )
    }

    private fun enrollStudentInSemesterSubjects(subjects: List<String>, enrollmentId: StudentSemesterEnrollmentId, valid: Boolean = true) {
        subjects.forEach { enrollInSubject(enrollmentId.id, it) }

        val subjectEnrollments =
            studentSubjectEnrollmentJpaRepository.findAllByStudentSubjectEnrollmentIdIn(subjects.map {
                StudentSubjectEnrollmentId(enrollmentId, SubjectCode(it))
            })
        assertEquals(subjects.size, subjectEnrollments.size, "There should be 5 enrolled subjects")

        // validate enrollment
        RestAssured.given()
            .contentType(ContentType.JSON)
            .put("/student-semester-enrollment/${enrollmentId.id}/validate")
            .then().log().all()
            .statusCode(200)
            .body(notNullValue())

        Thread.sleep(2000)
        entityManager.clear()
        var updatedEnrollment = studentSemesterEnrollmentJpaRepository.findByIdOrNull(enrollmentId)
        if (valid) {
            assertEquals(
                EnrollmentStatus.SUBJECTS_ADDED,
                updatedEnrollment?.getStatus(),
                "expected: <SUBJECTS_ADDED> but was: ${updatedEnrollment?.getStatus()}, for enrollment [${enrollmentId.id}]"
            )

            // students confirms enrollment
            RestAssured.given()
                .contentType(ContentType.JSON)
                .put("/student-semester-enrollment/${enrollmentId.id}/confirm")
                .then().log().all()
                .statusCode(200)
                .body(notNullValue())

            Thread.sleep(2000)
            entityManager.clear()
            updatedEnrollment = studentSemesterEnrollmentJpaRepository.findByIdOrNull(enrollmentId)

            assertEquals(EnrollmentStatus.STUDENT_CONFIRMED, updatedEnrollment?.getStatus())
        } else {
            assertEquals(
                EnrollmentStatus.INVALID,
                updatedEnrollment?.getStatus(),
                "expected: <INVALID> but was: ${updatedEnrollment?.getStatus()}, for enrollment [${enrollmentId.id}]"
            )
        }
    }

    private fun passSubjects(subjects: Map<String, Grade>, studentId: StudentId) {
        subjects.forEach { (subject, grade) ->
            studentRecordCommandService.subjectPassed(
                subjectCode = subject,
                studentId = studentId.index,
                grade = grade
            )
        }
        subjectSlotRepository.flush()
        entityManager.clear()
    }
}