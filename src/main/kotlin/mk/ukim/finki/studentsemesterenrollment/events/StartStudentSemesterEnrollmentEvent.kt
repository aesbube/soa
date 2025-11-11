package mk.ukim.finki.studentsemesterenrollment.events

import mk.ukim.finki.studentsemesterenrollment.commands.StartRegularEnrollmentCommand
import mk.ukim.finki.studentsemesterenrollment.valueObjects.*

data class StartStudentSemesterEnrollmentEvent(
    val id: StudentSemesterEnrollmentId,
): AbstractEvent<StudentSemesterEnrollmentId>(id) {
    constructor(
        command: StartRegularEnrollmentCommand,
    ) : this(
        StudentSemesterEnrollmentId(command.studentSemesterEnrollmentId.id)
    )
}

