package mk.ukim.finki.studentsemesterenrollment.commands

import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class EnrollStudentInFailedSubjectCommand (
    @TargetAggregateIdentifier
    val id: StudentSemesterEnrollmentId,
    val failedSubjectsCodes: List<SubjectCode>
) {
    constructor(command: EnrollStudentInFailedSubjectCommand) : this (
        command.id,
        command.failedSubjectsCodes
    )
}