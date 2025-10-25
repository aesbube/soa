package mk.ukim.finki.studentsemesterenrollment.commands

import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId

data class ConfirmEnrolledSubjectsCommand (
    val studentSemesterEnrollmentId: StudentSemesterEnrollmentId
) {
    constructor(command: ConfirmEnrolledSubjectsCommand) : this(
        studentSemesterEnrollmentId = command.studentSemesterEnrollmentId
    )
}