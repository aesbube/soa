package mk.ukim.finki.studentsemesterenrollment.service.impl

import mk.ukim.finki.studentsemesterenrollment.commands.EnrollStudentInFailedSubjectCommand
import mk.ukim.finki.studentsemesterenrollment.commands.ProvisionallyEnrollStudentOnSubjectCommand
import mk.ukim.finki.studentsemesterenrollment.commands.StartRegularEnrollmentCommand
import mk.ukim.finki.studentsemesterenrollment.commands.UpdatePaymentStatusCommand
import mk.ukim.finki.studentsemesterenrollment.commands.ValidateEnrollmentConditionsCommand
import mk.ukim.finki.studentsemesterenrollment.config.loggerFor
import mk.ukim.finki.studentsemesterenrollment.valueObjects.CycleSemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudyCycle
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode
import mk.ukim.finki.studentsemesterenrollment.valueObjects.previousSemesterId
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class StudentSemesterEnrollmentCommandService(
    private val commandGateway: CommandGateway,
) {

    private val logger = loggerFor<StudentSemesterEnrollmentCommandService>()

    // 1. Just init the enrollment (start the process of enrollment)
    fun startEnrollment(
        studentIndex: String,
        cycleId: String,
        semesterId: String,
    ): CompletableFuture<Void> {
        return commandGateway.send<Void>(
            StartRegularEnrollmentCommand(
                semesterCode = CycleSemesterId(
                    semesterId = SemesterId(semesterId),
                    cycle = StudyCycle.valueOf(cycleId)
                ),
                studentId = StudentId(studentIndex),
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
        }
    }

    // 2. Enroll in failed mandatory subjects
    fun enrollStudentInMandatoryFailedSubjects(
        studentIndex: String,
        semesterId: String,
        cycleId: String,
    ): CompletableFuture<Void> {
        return commandGateway.send(EnrollStudentInFailedSubjectCommand(
            id = StudentSemesterEnrollmentId(
                studentIndex = StudentId(studentIndex),
                semesterCode = CycleSemesterId(
                    semesterId = SemesterId(semesterId),
                    cycle = StudyCycle.valueOf(cycleId)
                )
            ),
            failedSubjectsCodes = listOf<SubjectCode>() // todo: fetch failed subjects
        ))
    }

    // 3. Provisionally enroll student
    fun provisionallyEnrollStudentOnSubject(
        id: String,
        subjectCode: String,
    ) {
        commandGateway.send<Void>(ProvisionallyEnrollStudentOnSubjectCommand(
            studentSemesterEnrollmentId = StudentSemesterEnrollmentId(id),
            subjectCode = SubjectCode(subjectCode)
        ))
    }

    fun validateEnrollmentConditions(
        id: String,
    ) {
        val enrollmentId = StudentSemesterEnrollmentId(id)
        commandGateway.send<Void>(ValidateEnrollmentConditionsCommand(
            studentSemesterEnrollmentId = enrollmentId,
            previousStudentSemesterEnrollmentId = StudentSemesterEnrollmentId(
                studentIndex = enrollmentId.studentIndex(),
                semesterCode = CycleSemesterId(
                    semesterId = enrollmentId.semesterCode().previousSemesterId,
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
}