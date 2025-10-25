package mk.ukim.finki.studentsemesterenrollment.model

import jakarta.persistence.*
import mk.ukim.finki.studentsemesterenrollment.commands.*
import mk.ukim.finki.studentsemesterenrollment.events.*
import mk.ukim.finki.studentsemesterenrollment.model.dto.StudentSemesterEnrollmentDto
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.StudentSubjectEnrollmentJpaRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SubjectJpaRepository
import mk.ukim.finki.studentsemesterenrollment.valueObjects.*
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime


@Entity
@Aggregate(repository = "axonStudentSemesterEnrollmentRepository")
class StudentSemesterEnrollment {
    @AggregateIdentifier
    @EmbeddedId
    private lateinit var id: StudentSemesterEnrollmentId

    @Embedded
    private lateinit var student: StudentId
    private lateinit var semester: CycleSemesterId

    @Enumerated(EnumType.STRING)
    private lateinit var enrollmentStatus: EnrollmentStatus

    @CreatedDate
    private lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    private lateinit var lastUpdatedAt: LocalDateTime

    @ElementCollection
    private val enrolledSubjects = mutableListOf<StudentSubjectEnrollmentId>()


    @CommandHandler
    constructor(
        command: StartRegularEnrollmentCommand,
    ) {
        val event = StartStudentSemesterEnrollmentEvent(command)
        this.on(event)
        AggregateLifecycle.apply(event)
    }

    fun on(event: StartStudentSemesterEnrollmentEvent) {
        this.id = event.id
        this.student = event.studentId
        this.semester = event.semesterCode
        this.enrollmentStatus = EnrollmentStatus.INITIATED
    }

    @CommandHandler
    fun updatePaymentStatus(command: ConfirmRegularEnrollmentCommand) {
        val event = ConfirmRegularEnrollmentEvent(command)

        this.on(event)
        AggregateLifecycle.apply(event)
    }

    fun on(event: UpdatePaymentStatusCommand) {
        this.enrollmentStatus = EnrollmentStatus.COMPLETED;
        this.lastUpdatedAt = LocalDateTime.now()
    }

    @CommandHandler
    fun confirmRegularEnrollment(command: ConfirmRegularEnrollmentCommand) {
        val event = ConfirmRegularEnrollmentEvent(command)

        this.on(event)
        AggregateLifecycle.apply(event)
    }

    fun on(event: ConfirmRegularEnrollmentEvent) {
        this.enrollmentStatus = EnrollmentStatus.STUDENT_CONFIRMED;
        this.lastUpdatedAt = LocalDateTime.now()
    }

    @CommandHandler
    fun selectReplacingSubjectForInvalidSubjectEnrollment(
        command: SelectReplacingSubjectForInvalidSubjectCommand, subjectJpaRepository: SubjectJpaRepository,
        studentSubjectEnrollmentJpaRepository: StudentSubjectEnrollmentJpaRepository
    ) {
        val subject = subjectJpaRepository.findById(command.subjectCode)
            .orElseThrow { IllegalArgumentException("Subject with code ${command.subjectCode} not found") }

        // TODO: Konsultacii val valid = validateDependencies

        val event = SelectReplacingSubjectForInvalidSubjectEvent(command)

        this.on(event)
        AggregateLifecycle.apply(event)
    }

    fun on(event: SelectReplacingSubjectForInvalidSubjectEvent) {
        this.lastUpdatedAt = LocalDateTime.now()
    }

    @CommandHandler
    fun enrollStudentInFailedSubject(
        command: EnrollStudentInFailedSubjectCommand, subjectJpaRepository: SubjectJpaRepository
    ) {
        val subjects = subjectJpaRepository.findAllByIdIn(command.failedSubjectsCodes)
        val event = EnrollStudentInFailedSubjectEvent(command)

        this.on(event)
        AggregateLifecycle.apply(event)
    }

    fun on(event: EnrollStudentInFailedSubjectEvent) {
        val enrolledSubjects = event.subjectsCodes.map { StudentSubjectEnrollmentId(event.id, subjectCode = it) }
        this.enrolledSubjects.addAll(enrolledSubjects)
        this.lastUpdatedAt = LocalDateTime.now()
    }

    @CommandHandler
    fun enrollStudentInSubject(
        command: EnrollStudentInSubjectCommand, subjectJpaRepository: SubjectJpaRepository,
        studentSubjectEnrollmentJpaRepository: StudentSubjectEnrollmentJpaRepository
    ) {
        val subject = subjectJpaRepository.findById(command.subjectCode)
            .orElseThrow { IllegalArgumentException("Subject with code ${command.subjectCode} not found") }

        // TODO: Konsultacii val valid = validateDependencies

        val studentSubjectEnrollment = studentSubjectEnrollmentJpaRepository.save(
            StudentSubjectEnrollment(
                studentSubjectEnrollmentId = StudentSubjectEnrollmentId(command.id, subjectCode = subject.id),
                subject = subject,
                valid = true
            )
        )
        val event = EnrollStudentInSubjectEvent(command)

        this.on(event)
        AggregateLifecycle.apply(event)
    }

    fun on(event: EnrollStudentInSubjectEvent) {
        this.enrolledSubjects.add(StudentSubjectEnrollmentId(event.id, subjectCode = event.subjectCode))
        this.lastUpdatedAt = LocalDateTime.now()
    }

    fun toDto() = StudentSemesterEnrollmentDto(
        index = student.index,
        cycleSemesterId = semester.value,
        lastModifiedDate = lastUpdatedAt,
        createdDate = createdAt,
    )

}
