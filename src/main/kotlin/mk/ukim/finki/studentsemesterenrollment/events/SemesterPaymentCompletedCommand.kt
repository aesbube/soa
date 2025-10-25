package mk.ukim.finki.studentsemesterenrollment.events

import mk.ukim.finki.studentsemesterenrollment.commands.UpdatePaymentStatusCommand
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId

data class SemesterPaymentCompletedCommand
    (
    val studentSemesterEnrollmentId: StudentSemesterEnrollmentId,
) {
    constructor(command: UpdatePaymentStatusCommand) : this(
        studentSemesterEnrollmentId = command.semesterEnrollmentId
    )
}