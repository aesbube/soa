package mk.ukim.finki.studentsemesterenrollment.commands

import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode

data class EnrollStudentInSubjectCommand (
    val id: StudentSemesterEnrollmentId,
    val subjectCode: SubjectCode
) {
    constructor(command: EnrollStudentInSubjectCommand) : this (
        command.id,
        command.subjectCode
    )
}