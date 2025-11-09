package mk.ukim.finki.studentsemesterenrollment.commands

import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId

data class ValidateEnrollmentConditionsCommand(
    val studentSemesterEnrollmentId: StudentSemesterEnrollmentId,
    val previousStudentSemesterEnrollmentId: StudentSemesterEnrollmentId,

    ){
    constructor(command: ValidateEnrollmentConditionsCommand) : this (
        command.studentSemesterEnrollmentId,
        command.previousStudentSemesterEnrollmentId
    )
}