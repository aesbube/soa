package mk.ukim.finki.studentsemesterenrollment.events

import mk.ukim.finki.studentsemesterenrollment.commands.StartRegularEnrollmentCommand
import mk.ukim.finki.studentsemesterenrollment.valueObjects.*

data class StartStudentSemesterEnrollmentEvent(
    val id: StudentSemesterEnrollmentId,
    val semesterCode: CycleSemesterId,
    val studentId: StudentId,
): AbstractEvent<StudentSemesterEnrollmentId>(id) {
    constructor(
        command: StartRegularEnrollmentCommand,
    ) : this(
        StudentSemesterEnrollmentId(command.semesterCode, command.studentId),
        command.semesterCode,
        command.studentId,
    )
}

