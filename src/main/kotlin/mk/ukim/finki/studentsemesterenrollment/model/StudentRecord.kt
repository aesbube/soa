package mk.ukim.finki.studentsemesterenrollment.model

import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import mk.ukim.finki.studentsemesterenrollment.aggregateSnapshot.SubjectAggregateSnapshot
import mk.ukim.finki.studentsemesterenrollment.client.AccreditationClient
import mk.ukim.finki.studentsemesterenrollment.commands.CreateStudentRecordCommand
import mk.ukim.finki.studentsemesterenrollment.commands.EnrollStudentInFailedSubjectCommand
import mk.ukim.finki.studentsemesterenrollment.events.EnrollStudentInFailedSubjectEvent
import mk.ukim.finki.studentsemesterenrollment.events.StudentRecordCreatedEvent
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SubjectJpaRepository
import mk.ukim.finki.studentsemesterenrollment.valueObjects.*
import org.axonframework.commandhandling.CommandHandler
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

    @OneToMany(mappedBy = "student")
    private var subjectSlots: MutableList<SubjectSlot> = mutableListOf()

    @OneToMany
    private var passedSubjects: MutableList<SubjectAggregateSnapshot> = mutableListOf()

    private lateinit var enrollmentYear: StudyYear
    private lateinit var winterSemesterNumber: Number
    private lateinit var summerSemesterNumber: Number

    private lateinit var createdAt: LocalDateTime

    @CommandHandler
    fun createStudentRecordCommand(
        command: CreateStudentRecordCommand,
        client: AccreditationClient
    ) {
        val subjects = client.getStudyProgramSubjects(studyProgram).body ?: emptyList()
        subjectSlots = subjects.mapIndexed { index, code ->
            SubjectSlot(
                    id = index.toLong(),
                    subjectId = code,
                    electiveSubjectGroup = null,
                    status = SubjectSlotStatus.NOT_ENROLLED,
                    student = this,
                    exam = null
            )
        }.toMutableList()

        val event = StudentRecordCreatedEvent(command)
        this.on(event)
        AggregateLifecycle.apply(event)
    }

    fun on(event: StudentRecordCreatedEvent) {
        this.createdAt = LocalDateTime.now()
    }
}