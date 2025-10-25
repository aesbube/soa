package mk.ukim.finki.studentsemesterenrollment.events

import mk.ukim.finki.studentsemesterenrollment.commands.ConfirmRegularEnrollmentCommand
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId

data class ConfirmRegularEnrollmentEvent(
    val id: StudentSemesterEnrollmentId,
): AbstractEvent<StudentSemesterEnrollmentId>(id) {
    constructor(
        command: ConfirmRegularEnrollmentCommand,
    ) : this(
        command.semesterEnrollmentId
    )
}

