package mk.ukim.finki.studentsemesterenrollment.service.impl

import mk.ukim.finki.studentsemesterenrollment.commands.ConfirmRegularEnrollmentCommand
import mk.ukim.finki.studentsemesterenrollment.commands.EnrollStudentInFailedSubjectCommand
import mk.ukim.finki.studentsemesterenrollment.commands.ProvisionallyEnrollStudentOnSubjectCommand
import mk.ukim.finki.studentsemesterenrollment.commands.StartRegularEnrollmentCommand
import mk.ukim.finki.studentsemesterenrollment.commands.UpdatePaymentStatusCommand
import mk.ukim.finki.studentsemesterenrollment.commands.ValidateEnrollmentConditionsCommand
import mk.ukim.finki.studentsemesterenrollment.config.loggerFor
import mk.ukim.finki.studentsemesterenrollment.model.StudentSemesterEnrollment
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.StudentRecordJpaRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.StudentSemesterEnrollmentJpaRepository
import mk.ukim.finki.studentsemesterenrollment.valueObjects.CycleSemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSubjectEnrollmentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudyCycle
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode
import mk.ukim.finki.studentsemesterenrollment.valueObjects.previousSemesterId
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class StudentSemesterEnrollmentCommandService(
    private val commandGateway: CommandGateway,
    private val studentRecordJpaRepository: StudentRecordJpaRepository,
    private val studentSemesterEnrollmentJpaRepository: StudentSemesterEnrollmentJpaRepository
) {

    private val logger = loggerFor<StudentSemesterEnrollmentCommandService>()

    // 1. Just init the enrollment (start the process of enrollment)
    fun startEnrollment(
        studentIndex: String,
        cycleId: String,
        semesterId: String,
    ): StudentSemesterEnrollmentId {
        return commandGateway.send<StudentSemesterEnrollmentId>(
            StartRegularEnrollmentCommand(
                studentSemesterEnrollmentId = StudentSemesterEnrollmentId(
                    semesterCode = CycleSemesterId(
                        semesterId = SemesterId(semesterId),
                        cycle = StudyCycle.valueOf(cycleId)
                    ),
                    studentIndex = StudentId(studentIndex),
                ),
            )
        ).thenCompose { _ ->
            enrollStudentInMandatoryFailedSubjects(
                studentIndex = studentIndex,
                semesterId = semesterId,
                cycleId = cycleId
            )
        }.exceptionally { throwable ->
            logger.debug("Error during enrollment process: ${throwable.message}")
            throw RuntimeException("Enrollment process failed", throwable)
        }.join()
    }

    // 2. Enroll in failed mandatory subjects
    fun enrollStudentInMandatoryFailedSubjects(
        studentIndex: String,
        semesterId: String,
        cycleId: String,
    ): CompletableFuture<StudentSemesterEnrollmentId> {

        val enrollmentId = StudentSemesterEnrollmentId(
            studentIndex = StudentId(studentIndex),
            semesterCode = CycleSemesterId(
                semesterId = SemesterId(semesterId),
                cycle = StudyCycle.valueOf(cycleId)
            )
        )

        val studentRecord = studentRecordJpaRepository.findById(StudentId(studentIndex))
            .orElseThrow { IllegalArgumentException("StudentRecord not found for $studentIndex") }

        val previousEnrollmentId = StudentSemesterEnrollmentId(
            studentIndex = StudentId(studentIndex),
            semesterCode = CycleSemesterId(
                semesterId = SemesterId(semesterId).previousSemesterId,
                cycle = StudyCycle.valueOf(cycleId)
            )
        )
        val prevEnrollment = studentSemesterEnrollmentJpaRepository.findByIdOrNull(previousEnrollmentId)
            ?: return CompletableFuture.completedFuture(enrollmentId)

        val prevEnrolledSubjects = prevEnrollment.getEnrolledSubjects()
        val passedSubjects = studentRecord.getPassedSubjects()

        val failedSubjects = prevEnrolledSubjects.map { it.subjectCode() }.filter { it !in passedSubjects }
//        val failedSubjects = studentRecord.computeFailedSubjects()

        if (failedSubjects.isEmpty()) {
            return CompletableFuture.completedFuture(enrollmentId)
        }

        // 4. Send the command to the aggregate
        val command = EnrollStudentInFailedSubjectCommand(
            id = enrollmentId,
            failedSubjectsCodes = failedSubjects
        )

        return commandGateway.send(command)
    }

    // 3. Provisionally enroll student
    fun provisionallyEnrollStudentOnSubject(
        id: String,
        subjectCode: String,
    ) = commandGateway.send<Any>(ProvisionallyEnrollStudentOnSubjectCommand(
            studentSemesterEnrollmentId = StudentSemesterEnrollmentId(id),
            subjectCode = SubjectCode(subjectCode)
        ))

    fun validateEnrollmentConditions(
        id: StudentSemesterEnrollmentId,
    ) {
        commandGateway.send<Void>(ValidateEnrollmentConditionsCommand(
            studentSemesterEnrollmentId = id,
            previousStudentSemesterEnrollmentId = StudentSemesterEnrollmentId(
                studentIndex = id.studentIndex(),
                semesterCode = CycleSemesterId(
                    semesterId = id.semesterCode().semesterId().previousSemesterId,
                    cycle = StudyCycle.UNDERGRADUATE
                )
            )
        ))
    }

    fun updatePaymentStatus(
        id: String,
    ) {
        commandGateway.send<Void>(UpdatePaymentStatusCommand(
            semesterEnrollmentId = StudentSemesterEnrollmentId(id)
        ))
    }

    fun confirmEnrollment(id: StudentSemesterEnrollmentId) {
        commandGateway.send<Any>(ConfirmRegularEnrollmentCommand(id))
    }
}