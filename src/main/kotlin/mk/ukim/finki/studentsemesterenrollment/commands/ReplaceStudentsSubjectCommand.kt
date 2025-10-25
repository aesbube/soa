package mk.ukim.finki.studentsemesterenrollment.commands

import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSubjectEnrollmentId

data class ReplaceStudentsSubjectCommand (
    val studentSemesterEnrollmentId: StudentSemesterEnrollmentId,
    val previousStudentSubjectEnrollmentId: StudentSubjectEnrollmentId,
    val newStudentSubjectEnrollmentId: StudentSubjectEnrollmentId,
){
    constructor(command: ReplaceStudentsSubjectCommand) : this(
        studentSemesterEnrollmentId = command.studentSemesterEnrollmentId,
        previousStudentSubjectEnrollmentId = command.previousStudentSubjectEnrollmentId,
        newStudentSubjectEnrollmentId = command.newStudentSubjectEnrollmentId,
    )

}