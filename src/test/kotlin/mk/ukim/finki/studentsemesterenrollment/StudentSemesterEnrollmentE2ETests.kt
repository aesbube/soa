package mk.ukim.finki.studentsemesterenrollment

import io.restassured.RestAssured
import io.restassured.config.LogConfig
import io.restassured.http.ContentType
import mk.ukim.finki.studentsemesterenrollment.aggregateSnapshot.SubjectAggregateSnapshot
import mk.ukim.finki.studentsemesterenrollment.config.loggerFor
import mk.ukim.finki.studentsemesterenrollment.handlers.EventMessagingEventHandler
import mk.ukim.finki.studentsemesterenrollment.kafka.KafkaProducer
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SemesterSnapshotRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SubjectJpaRepository
import mk.ukim.finki.studentsemesterenrollment.valueObjects.ClassesPerWeek
import mk.ukim.finki.studentsemesterenrollment.valueObjects.ECTSCredits
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterType
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectAbbreviation
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectName
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
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

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

    @LocalServerPort
    private val port: Int = 0

    @BeforeEach
    fun setup() {
        RestAssured.port = port
        RestAssured.baseURI = "http://localhost"
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
        RestAssured.config = RestAssured.config()
            .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails().enablePrettyPrinting(true))

    }

    @BeforeAll
    fun initData() {

        subjectSnapshotRepository.saveAll(
            listOf(
                SubjectAggregateSnapshot(
                    id = SubjectCode("F18L3W141"),
                    name = SubjectName("Test sub 1"),
                    abbreviation = SubjectAbbreviation("TS1"),
                    classesPerWeek = ClassesPerWeek(
                        auditoriumClasses = 3,
                        labClasses = 2,
                        lectures = 3
                    ),
                    ects = ECTSCredits(6),
                    semester = SemesterType.WINTER,
                    semesterCode = SemesterId("2025-26-W")
                ),
                SubjectAggregateSnapshot(
                    id = SubjectCode("F18L3W142"),
                    name = SubjectName("Test sub 2"),
                    abbreviation = SubjectAbbreviation("TS2"),
                    classesPerWeek = ClassesPerWeek(
                        auditoriumClasses = 3,
                        labClasses = 2,
                        lectures = 3
                    ),
                    ects = ECTSCredits(6),
                    semester = SemesterType.WINTER,
                    semesterCode = SemesterId("2025-26-W")
                ),
                SubjectAggregateSnapshot(
                    id = SubjectCode("F18L3W143"),
                    name = SubjectName("Test sub 3"),
                    abbreviation = SubjectAbbreviation("TS3"),
                    classesPerWeek = ClassesPerWeek(
                        auditoriumClasses = 3,
                        labClasses = 2,
                        lectures = 3
                    ),
                    ects = ECTSCredits(6),
                    semester = SemesterType.WINTER,
                    semesterCode = SemesterId("2025-26-W")
                ),
                SubjectAggregateSnapshot(
                    id = SubjectCode("F18L3W111"),
                    name = SubjectName("Test sub 4"),
                    abbreviation = SubjectAbbreviation("TS4"),
                    classesPerWeek = ClassesPerWeek(
                        auditoriumClasses = 3,
                        labClasses = 2,
                        lectures = 3
                    ),
                    ects = ECTSCredits(6),
                    semester = SemesterType.WINTER,
                    semesterCode = SemesterId("2025-26-W")
                ),
                SubjectAggregateSnapshot(
                    id = SubjectCode("F18L3W112"),
                    name = SubjectName("Test sub 5"),
                    abbreviation = SubjectAbbreviation("TS5"),
                    classesPerWeek = ClassesPerWeek(
                        auditoriumClasses = 3,
                        labClasses = 2,
                        lectures = 3
                    ),
                    ects = ECTSCredits(6),
                    semester = SemesterType.WINTER,
                    semesterCode = SemesterId("2025-26-W")
                )
            )
        )
    }

    @Test
    fun `should start enrollment for student successfully`() {
        val enrollmentRequest = """{
            "studentIndex": "216010",
            "cycleId": "UNDERGRADUATE",
            "semesterId": "2025-26-W"
        }"""

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(enrollmentRequest)
            .post("/student-semester-enrollment")
            .then().log().all()  // Log response details
            .statusCode(200)
            .body(notNullValue())
    }

    @Test
    fun `should start enrollment for different student`() {
        val enrollmentRequest = """{
            "studentIndex": "216048",
            "cycleId": "UNDERGRADUATE",
            "semesterId": "2025-26-W"
        }"""

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(enrollmentRequest)
            .post("/student-semester-enrollment")
            .then().log().all()  // Log response details
            .statusCode(200)
            .body(notNullValue())
    }

    @Test
    fun `should start enrollment for cyber security student`() {
        val enrollmentRequest = """{
            "studentIndex": "216047",
            "cycleId": "UNDERGRADUATE",
            "semesterId": "2025-26-W"
        }"""

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(enrollmentRequest)
            .post("/student-semester-enrollment")
            .then().log().all()  // Log response details
            .statusCode(200)
            .body(notNullValue())
    }

    @Test
    fun `should provisionally enroll student on subject`() {
        val enrollmentRequest = """{
            "studentIndex": "216046",
            "cycleId": "UNDERGRADUATE",
            "semesterId": "2025-26-W"
        }"""

        val enrollmentId = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(enrollmentRequest)
            .post("/student-semester-enrollment")
            .then().log().all()  // Log response details
            .statusCode(200)
            .extract()
            .asString()

        RestAssured.given()
            .contentType(ContentType.JSON)
            .put("/student-semester-enrollment/$enrollmentId/F18L3W141")
            .then().log().all()  // Log response details
            .statusCode(200)
    }

    @Test
    fun `should provisionally enroll on multiple subjects`() {
        val enrollmentRequest = """{
            "studentIndex": "216045",
            "cycleId": "UNDERGRADUATE",
            "semesterId": "2025-26-W"
        }"""

        val enrollmentId = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(enrollmentRequest)
            .post("/student-semester-enrollment")
            .then().log().all()  // Log response details
            .statusCode(200)
            .extract()
            .asString()

        val subjects = listOf("F18L3W141", "F18L3W142", "F18L3W143")

        subjects.forEach { subjectCode ->
            RestAssured.given()
                .contentType(ContentType.JSON)
                .put("/student-semester-enrollment/$enrollmentId/$subjectCode")
                .then().log().all()  // Log response details
                .statusCode(200)
        }
    }

    @Test
    fun `should validate student enrollment conditions`() {
        val enrollmentRequest = """{
            "studentIndex": "216044",
            "cycleId": "UNDERGRADUATE",
            "semesterId": "2025-26-W"
        }"""

        val enrollmentId = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(enrollmentRequest)
            .post("/student-semester-enrollment")
            .then().log().all()  // Log response details
            .statusCode(200)
            .extract()
            .asString()

        RestAssured.given()
            .contentType(ContentType.JSON)
            .put("/student-semester-enrollment/$enrollmentId/validate")
            .then().log().all()  // Log response details
            .statusCode(200)
    }

    @Test
    fun `should update payment status`() {
        val enrollmentRequest = """{
            "studentIndex": "216043",
            "cycleId": "UNDERGRADUATE",
            "semesterId": "2025-26-W"
        }"""

        val enrollmentId = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(enrollmentRequest)
            .post("/student-semester-enrollment")
            .then().log().all()  // Log response details
            .statusCode(200)
            .extract()
            .asString()

        RestAssured.given()
            .contentType(ContentType.JSON)
            .put("/student-semester-enrollment/$enrollmentId/update-payment")
            .then().log().all()  // Log response details
            .statusCode(200)
    }

    @Test
    fun `should confirm enrollment`() {
        val enrollmentRequest = """{
            "studentIndex": "216042",
            "cycleId": "UNDERGRADUATE",
            "semesterId": "2025-26-W"
        }"""

        val enrollmentId = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(enrollmentRequest)
            .post("/student-semester-enrollment")
            .then().log().all()  // Log response details
            .statusCode(200)
            .extract()
            .asString()

        RestAssured.given()
            .contentType(ContentType.JSON)
            .put("/student-semester-enrollment/$enrollmentId/confirm")
            .then().log().all()  // Log response details
            .statusCode(200)
    }

    @Test
    fun `should get all student semester enrollments`() {
        RestAssured.given()
            .contentType(ContentType.JSON)
            .get("/student-semester-enrollment")
            .then().log().all()  // Log response details
            .statusCode(200)
            .body(notNullValue())
    }

    @Test
    fun `should get student semester enrollment by id`() {
        val enrollmentRequest = """{
            "studentIndex": "216041",
            "cycleId": "UNDERGRADUATE",
            "semesterId": "2025-26-W"
        }"""

        val enrollmentId = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(enrollmentRequest)
            .post("/student-semester-enrollment")
            .then().log().all()  // Log response details
            .statusCode(200)
            .extract()
            .asString()

        RestAssured.given()
            .contentType(ContentType.JSON)
            .get("/student-semester-enrollment/$enrollmentId")
            .then().log().all()  // Log response details
            .statusCode(200)
            .body(notNullValue())
    }

    @Test
    fun `complete enrollment workflow - start, enroll subjects, validate, pay, confirm`() {
        // Start enrollment
        val enrollmentRequest = """{
            "studentIndex": "216040",
            "cycleId": "UNDERGRADUATE",
            "semesterId": "2025-26-W"
        }"""

        val enrollmentId = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(enrollmentRequest)
            .post("/student-semester-enrollment")
            .then().log().all()  // Log response details
            .statusCode(200)
            .extract()
            .asString()

        logger.debug("AAAAAAAAA ${enrollmentId}")
        println("AAAAAAAAA ${enrollmentId}")

        // Enroll on subjects
        val subjects = listOf("F18L3W141", "F18L3W142", "F18L3W143")
        subjects.forEach { subjectCode ->
            RestAssured.given()
                .contentType(ContentType.JSON)
                .put("/student-semester-enrollment/$enrollmentId/$subjectCode")
                .then().log().all()  // Log response details
                .statusCode(200)
        }

        // Validate enrollment
        RestAssured.given()
            .contentType(ContentType.JSON)
            .put("/student-semester-enrollment/$enrollmentId/validate")
            .then().log().all()  // Log response details
            .statusCode(200)

        // Update payment
        RestAssured.given()
            .contentType(ContentType.JSON)
            .put("/student-semester-enrollment/$enrollmentId/update-payment")
            .then().log().all()  // Log response details
            .statusCode(200)

        // Confirm enrollment
        RestAssured.given()
            .contentType(ContentType.JSON)
            .put("/student-semester-enrollment/$enrollmentId/confirm")
            .then().log().all()  // Log response details
            .statusCode(200)
    }

    @Test
    fun `should start enrollment for multiple students in same cycle`() {
        val students = listOf("211234", "211235", "211236")

        students.forEach { studentIndex ->
            val enrollmentRequest = """{
                "studentIndex": "$studentIndex",
                "cycleId": "UNDERGRADUATE",
                "semesterId": "2025-26-W"
            }"""

            RestAssured.given()
                .contentType(ContentType.JSON)
                .body(enrollmentRequest)
                .post("/student-semester-enrollment")
                .then().log().all()  // Log response details
                .statusCode(200)
                .body(notNullValue())
        }
    }

    @Test
    fun `should start enrollment for different semesters`() {
        val semesters = listOf("2025-26-W", "2025-26-S", "2024-25-W")

        semesters.forEachIndexed { index, semesterId ->
            val enrollmentRequest = """{
                "studentIndex": "22123${index + 4}",
                "cycleId": "UNDERGRADUATE",
                "semesterId": "$semesterId"
            }"""

            RestAssured.given()
                .contentType(ContentType.JSON)
                .body(enrollmentRequest)
                .post("/student-semester-enrollment")
                .then().log().all()  // Log response details
                .statusCode(200)
                .body(notNullValue())
        }
    }

    @Test
    fun `should handle enrollment with software engineering subjects`() {
        val enrollmentRequest = """{
            "studentIndex": "191001",
            "cycleId": "UNDERGRADUATE",
            "semesterId": "2025-26-W"
        }"""

        val enrollmentId = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(enrollmentRequest)
            .post("/student-semester-enrollment")
            .then().log().all()  // Log response details
            .statusCode(200)
            .extract()
            .asString()

        val subjects = listOf("F18L3W111", "F18L3W112")
        subjects.forEach { subjectCode ->
            RestAssured.given()
                .contentType(ContentType.JSON)
                .put("/student-semester-enrollment/$enrollmentId/$subjectCode")
                .then().log().all()  // Log response details
                .statusCode(200)
        }
    }

    @Test
    fun `should start enrollment for new academic year`() {
        val enrollmentRequest = """{
            "studentIndex": "241001",
            "cycleId": "UNDERGRADUATE",
            "semesterId": "2025-26-W"
        }"""

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(enrollmentRequest)
            .post("/student-semester-enrollment")
            .then().log().all()  // Log response details
            .statusCode(200)
            .body(notNullValue())
    }
}