package mk.ukim.finki.studentsemesterenrollment.model

import jakarta.persistence.*
import mk.ukim.finki.studentsemesterenrollment.client.SemesterClient
import mk.ukim.finki.studentsemesterenrollment.commands.*
import mk.ukim.finki.studentsemesterenrollment.config.loggerFor
import mk.ukim.finki.studentsemesterenrollment.events.*
import mk.ukim.finki.studentsemesterenrollment.model.dto.StudentSemesterEnrollmentDto
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SemesterSnapshotRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.StudentRecordJpaRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.StudentSemesterEnrollmentJpaRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.StudentSubjectEnrollmentJpaRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SubjectJpaRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SubjectSlotRepository
import mk.ukim.finki.studentsemesterenrollment.valueObjects.*
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.modelling.command.Repository
import org.axonframework.spring.stereotype.Aggregate
import org.springframework.cglib.core.Local
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.concurrent.CompletableFuture
import kotlin.jvm.optionals.getOrNull


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
        semesterRepository: SemesterSnapshotRepository,
        studentRepository: StudentRecordJpaRepository
    ) {
        val id = command.studentSemesterEnrollmentId
        val semesterCode = id.semesterCode()
        val studentIndex = id.studentIndex()

        val semester = semesterRepository.findByIdOrNull(semesterCode.semesterId())
            ?: throw RuntimeException("Semester with code ${semesterCode.semesterId()} not found")

        if (semester.state != SemesterState.STUDENTS_ENROLLMENT) {
            throw RuntimeException("Can not enroll in semester ${semester.id}. Semester state isn't ${SemesterState.STUDENTS_ENROLLMENT} but ${semester.state}")
        }

        val now = LocalDateTime.now()

        if (now < semester.enrollmentStartDate) {
            throw RuntimeException("Can not enroll in semester ${semester.id}. Enrollment has not started. Now: $now enrollmentStartDate ${semester.enrollmentStartDate}")
        }

        if (now > semester.enrollmentEndDate) {
            throw RuntimeException("Can not enroll in semester ${semester.id}. Enrollment has finished. Now: $now enrollmentEndDate ${semester.enrollmentEndDate}")
        }

        studentRepository.findByIdOrNull(studentIndex) ?: throw RuntimeException("No student found with index ${studentIndex}.")

        val event = StartStudentSemesterEnrollmentEvent(command)
        this.on(event)
        AggregateLifecycle.apply(event)
    }

//    @CommandHandler
//    fun startEnrollment(command: StartRegularEnrollmentCommand) {
//        val event = StartStudentSemesterEnrollmentEvent(command)
//        this.on(event)
//        AggregateLifecycle.apply(event)
//    }

    fun on(event: StartStudentSemesterEnrollmentEvent) {
        this.id = event.id
        this.student = event.id.studentIndex()
        this.semester = event.id.semesterCode()
        this.enrollmentStatus = EnrollmentStatus.INITIATED
    }

    @CommandHandler
    fun updatePaymentStatus(command: UpdatePaymentStatusCommand) {
        val event = UpdatePaymentStatusEvent(command)

        this.on(event)
        AggregateLifecycle.apply(event)
    }

    fun on(event: UpdatePaymentStatusEvent) {
        this.enrollmentStatus = EnrollmentStatus.COMPLETED;
        this.lastUpdatedAt = LocalDateTime.now()
    }

    @CommandHandler
    fun confirmRegularEnrollment(
        command: ConfirmRegularEnrollmentCommand, subjectSlotRepository: SubjectSlotRepository
    ) {
        val event = ConfirmRegularEnrollmentEvent(command)

        println("AAAAAAAAAAAAA")
        loggerFor<StudentSemesterEnrollment>().debug("AAAAAAAAAAA")
        loggerFor<StudentSemesterEnrollment>().debug(enrolledSubjects.joinToString { it.toString() })
        enrolledSubjects.map {
            SubjectSlot(
                subjectId = it.subjectCode(),
                electiveSubjectGroup = it.electiveSubjectGroup(),
                status = SubjectSlotStatus.ENROLLED,
                studentId = it.semesterEnrollmentId().studentIndex(),
                exam = null,
            )
        }.let {
            loggerFor<StudentSemesterEnrollment>().debug(it.joinToString { it.toString() })
            subjectSlotRepository.saveAll(it)
        }

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
        command: EnrollStudentInFailedSubjectCommand,
        subjectJpaRepository: SubjectJpaRepository,
    ): StudentSemesterEnrollmentId {
        val subjects = subjectJpaRepository.findAllByIdIn(command.failedSubjectsCodes)
        val event = EnrollStudentInFailedSubjectEvent(command)

        this.on(event)
        AggregateLifecycle.apply(event)
        return this.id
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

    @CommandHandler
    fun provisionallyEnrollStudentOnSubject(
        command: ProvisionallyEnrollStudentOnSubjectCommand,
        subjectJpaRepository: SubjectJpaRepository,
        studentSubjectEnrollmentJpaRepository: StudentSubjectEnrollmentJpaRepository
    ) {
        val subject = subjectJpaRepository.findById(command.subjectCode)
            .orElseThrow { IllegalArgumentException("Subject with code ${command.subjectCode} not found") }

        if (this.enrollmentStatus != EnrollmentStatus.INITIATED) {
            throw IllegalStateException("Cannot provisionally enroll: Enrollment is not in INITIATED status")
        }

        if (this.enrolledSubjects.any { it.subjectCode() == command.subjectCode }) {
            throw IllegalStateException("Student is already enrolled in subject ${command.subjectCode}")
        }
        val event = StudentProvisionallyEnrolledOnSubjectEvent(command = command)

        studentSubjectEnrollmentJpaRepository.save(StudentSubjectEnrollment(
            studentSubjectEnrollmentId = StudentSubjectEnrollmentId(
                semesterEnrollmentId = event.studentSemesterEnrollmentId,
                subjectCode = event.subjectCode,
            ),
            subject = subject,
            valid = true
        ))
        this.on(event)
        AggregateLifecycle.apply(event)
    }

    fun on(event: StudentProvisionallyEnrolledOnSubjectEvent) {
        this.enrolledSubjects.add(
            StudentSubjectEnrollmentId(
                semesterEnrollmentId = event.studentSemesterEnrollmentId,
                subjectCode = event.subjectCode,
            )
        )
        this.lastUpdatedAt = LocalDateTime.now()
    }

    private fun calculateCurrentECTS(subjectJpaRepository: SubjectJpaRepository): Int {
        return this.enrolledSubjects.sumOf { enrollment ->
            subjectJpaRepository.findById(enrollment.subjectCode())
                .map { it.ects.credits }
                .orElse(0)
        }
    }

    @CommandHandler
    fun validateEnrollmentConditions(
        command: ValidateEnrollmentConditionsCommand,
        studentSemesterEnrollmentJpaRepository: StudentSemesterEnrollmentJpaRepository,
        commandGateway: CommandGateway
    ) {
        val previousSemesterEnrollment =
            studentSemesterEnrollmentJpaRepository.findById(command.previousStudentSemesterEnrollmentId).getOrNull()

        // todo: validations...
        val valid = validate()

        when (valid) {
            true -> {
                val event = EnrollmentConditionsValidatedEvent(command)
                this.on(event, commandGateway)
                AggregateLifecycle.apply(event)
            }

            false -> {
                val event = EnrollmentConditionsValidationFailedEvent(command)
                this.on(event)
                AggregateLifecycle.apply(event)
            }
        }
    }

    private fun validate(): Boolean {
        return true
    }

    fun on(event: EnrollmentConditionsValidatedEvent, commandGateway: CommandGateway) {
        this.enrollmentStatus = EnrollmentStatus.SUBJECTS_ADDED
        this.lastUpdatedAt = LocalDateTime.now()

        commandGateway.send<Any>(ConfirmEnrolledSubjectsCommand(event.studentSemesterEnrollmentId))
    }

    fun on(event: EnrollmentConditionsValidationFailedEvent) {
        this.enrollmentStatus = EnrollmentStatus.INVALID
        this.lastUpdatedAt = LocalDateTime.now()
    }

    @CommandHandler
    fun confirmEnrollment(command: ConfirmEnrolledSubjectsCommand) {
        val event = StudentConfirmedEnrolledSubjectsEvent(command)
        this.on(event)
        AggregateLifecycle.apply(event)
    }

    fun on(event: StudentConfirmedEnrolledSubjectsEvent) {
        this.enrollmentStatus = EnrollmentStatus.STUDENT_CONFIRMED
        this.lastUpdatedAt = LocalDateTime.now()
    }

    fun toDto() = StudentSemesterEnrollmentDto(
        index = student.index,
        cycleSemesterId = semester.value,
    )

}
