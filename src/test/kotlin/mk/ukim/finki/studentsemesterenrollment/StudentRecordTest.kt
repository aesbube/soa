package mk.ukim.finki.studentsemesterenrollment

import mk.ukim.finki.studentsemesterenrollment.client.AccreditationClient
import mk.ukim.finki.studentsemesterenrollment.commands.CreateStudentRecordCommand
import mk.ukim.finki.studentsemesterenrollment.events.StudentRecordCreatedEvent
import mk.ukim.finki.studentsemesterenrollment.model.StudentRecord
import mk.ukim.finki.studentsemesterenrollment.valueObjects.*
import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class StudentRecordTest {

    private lateinit var fixture: AggregateTestFixture<StudentRecord>
    private lateinit var mockAccreditationClient: AccreditationClient

    @BeforeEach
    fun setup() {
        fixture = AggregateTestFixture(StudentRecord::class.java)
        mockAccreditationClient = mock(AccreditationClient::class.java)

        fixture.registerInjectableResource(mockAccreditationClient)
    }

    @Test
    fun `should create student record successfully`() {
        val studentId = StudentId("211234")
        val ects = ECTSCredits(0)
        val studyProgram = StudyProgram.Type.CYBER_SECURITY.toStudyProgram()

        val command = CreateStudentRecordCommand(
            id = studentId,
            ects = ects,
            studyProgram = studyProgram
        )

        val mockSubjects = listOf(
            SubjectCode("F18L1W141"),
            SubjectCode("F18L3W142"),
            SubjectCode("F18L3W143")
        )

        whenever(mockAccreditationClient.getStudyProgramSubjects(studyProgram))
            .thenReturn(mockSubjects)

        val expectedEvent = StudentRecordCreatedEvent(command, mockSubjects)

        fixture.givenNoPriorActivity()
            .`when`(command)
            .expectEvents(expectedEvent)
            .expectSuccessfulHandlerExecution()
    }

    @Test
    fun `should create student record with correct student id`() {
        val studentId = StudentId("191001")
        val ects = ECTSCredits(60)
        val studyProgram = StudyProgram.Type.SOFTWARE_ENGINEERING.toStudyProgram()

        val command = CreateStudentRecordCommand(
            id = studentId,
            ects = ects,
            studyProgram = studyProgram
        )

        val mockSubjects = listOf(
            SubjectCode("F18L3W111"),
            SubjectCode("F18L3W112")
        )

        whenever(mockAccreditationClient.getStudyProgramSubjects(studyProgram))
            .thenReturn(mockSubjects)

        val expectedEvent = StudentRecordCreatedEvent(command, mockSubjects)

        fixture.givenNoPriorActivity()
            .`when`(command)
            .expectEvents(expectedEvent)
            .expectSuccessfulHandlerExecution()
    }

    @Test
    fun `should fail when creating student record with invalid student id`() {
        org.junit.jupiter.api.assertThrows<IllegalArgumentException> {
            StudentId("1234")
        }
    }

    @Test
    fun `should fail when creating student record with non-numeric student id`() {
        org.junit.jupiter.api.assertThrows<IllegalArgumentException> {
            StudentId("ABC123")
        }
    }

    @Test
    fun `should create student record for different study programs`() {
        val studentId = StudentId("201234")
        val ects = ECTSCredits(30)
        val studyProgram = StudyProgram.Type.CYBER_SECURITY.toStudyProgram()

        val command = CreateStudentRecordCommand(
            id = studentId,
            ects = ects,
            studyProgram = studyProgram
        )

        val mockSubjects = listOf(
            SubjectCode("F18L1W141"),
            SubjectCode("F18L3W142"),
            SubjectCode("F18L3W143")
        )

        whenever(mockAccreditationClient.getStudyProgramSubjects(studyProgram))
            .thenReturn(mockSubjects)

        val expectedEvent = StudentRecordCreatedEvent(command, mockSubjects)

        fixture.givenNoPriorActivity()
            .`when`(command)
            .expectEvents(expectedEvent)
            .expectSuccessfulHandlerExecution()
    }

    @Test
    fun `should handle empty subject list from accreditation client`() {
        val studentId = StudentId("211234")
        val ects = ECTSCredits(0)
        val studyProgram = StudyProgram("Unknown Program Name")

        val command = CreateStudentRecordCommand(
            id = studentId,
            ects = ects,
            studyProgram = studyProgram
        )

        whenever(mockAccreditationClient.getStudyProgramSubjects(studyProgram))
            .thenReturn(emptyList())

        val expectedEvent = StudentRecordCreatedEvent(command, emptyList())

        fixture.givenNoPriorActivity()
            .`when`(command)
            .expectEvents(expectedEvent)
            .expectSuccessfulHandlerExecution()
    }
}