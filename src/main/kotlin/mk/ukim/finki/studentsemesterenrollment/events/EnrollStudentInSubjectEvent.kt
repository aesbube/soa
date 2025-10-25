package mk.ukim.finki.studentsemesterenrollment.events

import mk.ukim.finki.studentsemesterenrollment.commands.EnrollStudentInSubjectCommand
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode

data class EnrollStudentInSubjectEvent(
    val id: StudentSemesterEnrollmentId,
    val subjectCode: SubjectCode
): AbstractEvent<StudentSemesterEnrollmentId>(id) {
    constructor(command: EnrollStudentInSubjectCommand) : this(
        command.id,
        command.subjectCode
    )
}
