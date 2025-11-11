package mk.ukim.finki.studentsemesterenrollment.commands

import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class ProvisionallyEnrollStudentOnSubjectCommand (
    @TargetAggregateIdentifier
    val studentSemesterEnrollmentId: StudentSemesterEnrollmentId,
    val subjectCode: SubjectCode,
){
    constructor(command: ProvisionallyEnrollStudentOnSubjectCommand) : this(
        studentSemesterEnrollmentId = command.studentSemesterEnrollmentId,
        subjectCode = command.subjectCode,
    )
}