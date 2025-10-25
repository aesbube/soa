package mk.ukim.finki.studentsemesterenrollment.commands

import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSubjectEnrollmentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode

data class SelectReplacingSubjectForInvalidSubjectCommand (
    val id: StudentSemesterEnrollmentId,
    val subjectId: StudentSubjectEnrollmentId,
    val subjectCode: SubjectCode
) {
    constructor(command: SelectReplacingSubjectForInvalidSubjectCommand) : this (
        command.id,
        command.subjectId,
        command.subjectCode
    )
}
