package mk.ukim.finki.studentsemesterenrollment.model

import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import mk.ukim.finki.studentsemesterenrollment.aggregateSnapshot.SubjectAggregateSnapshot
import mk.ukim.finki.studentsemesterenrollment.client.AccreditationClient
import mk.ukim.finki.studentsemesterenrollment.commands.CreateStudentRecordCommand
import mk.ukim.finki.studentsemesterenrollment.events.StudentRecordCreatedEvent
import mk.ukim.finki.studentsemesterenrollment.valueObjects.*
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
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

    @OneToMany(mappedBy = "studentId")
    private var subjectSlots: MutableList<SubjectSlot> = mutableListOf()

    @OneToMany
    private var passedSubjects: MutableList<SubjectAggregateSnapshot> = mutableListOf()

    private lateinit var enrollmentYear: StudyYear
    private var winterSemesterNumber: Int = 0
    private var summerSemesterNumber: Int = 0

    private lateinit var createdAt: LocalDateTime

    constructor()

    @CommandHandler
    constructor(
        command: CreateStudentRecordCommand,
        client: AccreditationClient
    ) {
        val subjects = client.getStudyProgramSubjects(command.studyProgram)

        val event = StudentRecordCreatedEvent(
            command = command,
            subjects = subjects
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

        this.subjectSlots = event.subjects.mapIndexed { index, code ->
            SubjectSlot(
                id = index.toLong(),
                subjectId = code,
                electiveSubjectGroup = null,
                status = SubjectSlotStatus.NOT_ENROLLED,
                studentId = this.id,
                exam = null
            )
        }.toMutableList()
    }
}