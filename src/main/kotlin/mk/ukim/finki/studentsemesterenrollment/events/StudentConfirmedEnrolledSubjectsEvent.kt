package mk.ukim.finki.studentsemesterenrollment.events

import mk.ukim.finki.studentsemesterenrollment.commands.ConfirmEnrolledSubjectsCommand
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId

data class StudentConfirmedEnrolledSubjectsEvent (
    val studentSemesterEnrollmentId: StudentSemesterEnrollmentId,
) : AbstractEvent<StudentSemesterEnrollmentId>(studentSemesterEnrollmentId){
    constructor(command: ConfirmEnrolledSubjectsCommand) : this(
        studentSemesterEnrollmentId = command.studentSemesterEnrollmentId,
    )
}