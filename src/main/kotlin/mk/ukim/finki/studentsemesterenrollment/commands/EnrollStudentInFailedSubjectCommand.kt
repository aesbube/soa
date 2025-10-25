package mk.ukim.finki.studentsemesterenrollment.commands

import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode

data class EnrollStudentInFailedSubjectCommand (
    val id: StudentSemesterEnrollmentId,
    val failedSubjectsCodes: List<SubjectCode>
) {
    constructor(command: EnrollStudentInFailedSubjectCommand) : this (
        command.id,
        command.failedSubjectsCodes
    )
}