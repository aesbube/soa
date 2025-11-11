package mk.ukim.finki.studentsemesterenrollment.events

import mk.ukim.finki.studentsemesterenrollment.commands.ValidateEnrollmentConditionsCommand
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId

data class EnrollmentConditionsValidatedEvent
    (
    val studentSemesterEnrollmentId: StudentSemesterEnrollmentId,
    val previousStudentSemesterEnrollmentId: StudentSemesterEnrollmentId,
) : AbstractEvent<StudentSemesterEnrollmentId>(studentSemesterEnrollmentId) {
    constructor(command: ValidateEnrollmentConditionsCommand) : this(
        studentSemesterEnrollmentId = command.studentSemesterEnrollmentId,
        previousStudentSemesterEnrollmentId = command.previousStudentSemesterEnrollmentId
    )
}

data class EnrollmentConditionsValidationFailedEvent(
    val studentSemesterEnrollmentId: StudentSemesterEnrollmentId,
): AbstractEvent<StudentSemesterEnrollmentId>(studentSemesterEnrollmentId) {
    constructor(command: ValidateEnrollmentConditionsCommand) : this(
        studentSemesterEnrollmentId = command.studentSemesterEnrollmentId,
    )
}