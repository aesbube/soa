package mk.ukim.finki.studentsemesterenrollment.events

import mk.ukim.finki.studentsemesterenrollment.commands.EnrollStudentInFailedSubjectCommand
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode

data class EnrollStudentInFailedSubjectEvent(
    val id: StudentSemesterEnrollmentId,
    val subjectsCodes: List<SubjectCode>
): AbstractEvent<StudentSemesterEnrollmentId>(id) {
    constructor(command: EnrollStudentInFailedSubjectCommand) : this(
        command.id,
        command.failedSubjectsCodes
    )
}
