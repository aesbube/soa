package mk.ukim.finki.studentsemesterenrollment.commands

import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class ValidateEnrollmentConditionsCommand(
    @TargetAggregateIdentifier
    val studentSemesterEnrollmentId: StudentSemesterEnrollmentId,
    val previousStudentSemesterEnrollmentId: StudentSemesterEnrollmentId,

    ){
    constructor(command: ValidateEnrollmentConditionsCommand) : this (
        command.studentSemesterEnrollmentId,
        command.previousStudentSemesterEnrollmentId
    )
}