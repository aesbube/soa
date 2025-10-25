package mk.ukim.finki.studentsemesterenrollment.events

import mk.ukim.finki.studentsemesterenrollment.commands.ReplaceStudentsSubjectCommand
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSubjectEnrollmentId

data class StudentServicesSubjectReplacedAfterEnrollmentEvent (
    val studentSemesterEnrollmentId: StudentSemesterEnrollmentId,
    val previousStudentSubjectEnrollmentId: StudentSubjectEnrollmentId,
    val newStudentSubjectEnrollment: StudentSubjectEnrollmentId,
) : AbstractEvent<StudentSemesterEnrollmentId>(studentSemesterEnrollmentId) {
    constructor(command: ReplaceStudentsSubjectCommand) : this(
        command.studentSemesterEnrollmentId,
        command.previousStudentSubjectEnrollmentId,
        command.newStudentSubjectEnrollmentId
    )
}