package mk.ukim.finki.studentsemesterenrollment.events

import mk.ukim.finki.studentsemesterenrollment.commands.UpdatePaymentStatusCommand
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId

data class UpdatePaymentStatusEvent(
    val id: StudentSemesterEnrollmentId,
): AbstractEvent<StudentSemesterEnrollmentId>(id) {
    constructor(
        command: UpdatePaymentStatusCommand,
    ) : this(
        command.semesterEnrollmentId
    )
}

