package mk.ukim.finki.studentsemesterenrollment.model

import jakarta.persistence.ElementCollection
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import mk.ukim.finki.studentsemesterenrollment.client.AccreditationClient
import mk.ukim.finki.studentsemesterenrollment.commands.CreateStudentRecordCommand
import mk.ukim.finki.studentsemesterenrollment.commands.SubjectExamCommand
import mk.ukim.finki.studentsemesterenrollment.events.StudentRecordCreatedEvent
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SubjectExamRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SubjectJpaRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SubjectSlotRepository
import mk.ukim.finki.studentsemesterenrollment.valueObjects.*
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime

@Entity
@Aggregate(repository = "axonStudentRecordRepository")
class StudentRecord {
    @EmbeddedId
    @AggregateIdentifier
    private lateinit var id: StudentId

    @Embedded
    private lateinit var ects: ECTSCredits

    @Embedded
    private lateinit var gpa: GPA

    @Embedded
    private lateinit var studyProgram: StudyProgram

    @ElementCollection
    private var subjectSlots: MutableList<SubjectSlotId> = mutableListOf()

    @ElementCollection
    private var passedSubjects: MutableList<SubjectCode> = mutableListOf()

    @ElementCollection
    private var grades: MutableList<Grade> = mutableListOf()

    private lateinit var enrollmentYear: StudyYear
    private var winterSemesterNumber: Int = 0
    private var summerSemesterNumber: Int = 0

    private lateinit var createdAt: LocalDateTime

    constructor()

    @CommandHandler
    constructor(
        command: CreateStudentRecordCommand,
        client: AccreditationClient,
        subjectSlotRepository: SubjectSlotRepository
    ) {
        val subjects = client.getStudyProgramSubjects(command.studyProgram)

        subjects.map { code ->
            SubjectSlot(
                id = SubjectSlotId(code, command.id),
                subjectId = code,
                electiveSubjectGroup = null,
                status = SubjectSlotStatus.NOT_ENROLLED,
                studentId = command.id,
                exam = null,
                mandatory = !code.isPlaceholder,
                placeholder = code.isPlaceholder,
            )
        }.let {
            subjectSlotRepository.saveAll(it)
        }
        val event = StudentRecordCreatedEvent(
            command = command,
            subjects = subjects.toMutableList(),
        )

        AggregateLifecycle.apply(event)
    }

    @EventSourcingHandler
    fun on(event: StudentRecordCreatedEvent) {
        this.id = event.id
        this.ects = event.ects
        this.studyProgram = event.studyProgram
        this.gpa = GPA(5.0)
        this.enrollmentYear = event.id.enrollmentYear()
        this.winterSemesterNumber = 0
        this.summerSemesterNumber = 0
        this.createdAt = LocalDateTime.now().withNano(0)
        this.passedSubjects = mutableListOf()
        this.grades = mutableListOf()

        this.subjectSlots = event.subjects.map {
            SubjectSlotId(
                it, event.id
            )
        }.toMutableList()
    }

    @CommandHandler
    fun subjectPassed(
        command: SubjectExamCommand,
        subjectRepository: SubjectJpaRepository,
        subjectExamRepository: SubjectExamRepository,
        subjectSlotRepository: SubjectSlotRepository
    ) {

        val subject = subjectRepository.findByIdOrNull(command.subjectCode) ?: throw RuntimeException(
            "Subject code ${command.subjectCode} not found"
        )
        val slot = subjectSlotRepository.findBySubjectIdAndStudentId(command.subjectCode.value, command.studentId.index)!!
        val exam = subjectExamRepository.save(
            SubjectExam(
                professor = command.professorId,
                grade = command.grade,
                datePassed = command.datePassed,
                externalId = command.externalId,
                subjectSlot = slot,
                id = 0L
            )
        )
        subjectSlotRepository.save(
            SubjectSlot(
                id = slot.id,
                exam = exam,
                subjectId = slot.subjectId,
                electiveSubjectGroup = slot.electiveSubjectGroup,
                status = SubjectSlotStatus.PASSED,
                studentId = slot.studentId,
                mandatory = slot.mandatory,
                placeholder = slot.placeholder,
            )
        )
        val event = StudentPassedSubjectEvent(
            subject.id,
            exam
        )
        AggregateLifecycle.apply(event)
    }

    @EventSourcingHandler
    fun on(event: StudentPassedSubjectEvent) {
        this.passedSubjects.add(event.subject)
        this.grades.add(event.exam.grade)

        this.gpa = this.calculateGPA()
    }

    private fun calculateGPA(): GPA {
        if (passedSubjects.isEmpty()) {
            return GPA(5.0)
        }

        val totalGrades = grades.sumOf { it.grade }.toDouble()
        val averageGrade = totalGrades / grades.size

        return GPA(averageGrade)
    }

    fun getPassedSubjects() = buildList { addAll(passedSubjects) }
    fun getSubjectSlots() = buildList { addAll(subjectSlots) }
    fun getGPA() = gpa.copy()
}

data class StudentPassedSubjectEvent(
    val subject: SubjectCode,
    val exam: SubjectExam
)