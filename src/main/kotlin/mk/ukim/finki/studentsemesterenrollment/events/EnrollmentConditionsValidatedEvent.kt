package mk.ukim.finki.studentsemesterenrollment.events

import mk.ukim.finki.studentsemesterenrollment.commands.ValidateEnrollmentConditionsCommand
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId

data class EnrollmentConditionsValidatedEvent
    (
    val studentSemesterEnrollmentId: StudentSemesterEnrollmentId,
    val previousStudentSemesterEnrollmentid: StudentSemesterEnrollmentId,
) : AbstractEvent<StudentSemesterEnrollmentId>(studentSemesterEnrollmentId) {
    constructor(command: ValidateEnrollmentConditionsCommand) : this(
        studentSemesterEnrollmentId = command.studentSemesterEnrollmentId,
        previousStudentSemesterEnrollmentid = command.previousStudentSemesterEnrollmentid
    )
}