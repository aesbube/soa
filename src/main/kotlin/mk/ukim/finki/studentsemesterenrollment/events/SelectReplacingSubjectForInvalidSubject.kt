package mk.ukim.finki.studentsemesterenrollment.events

import mk.ukim.finki.studentsemesterenrollment.commands.SelectReplacingSubjectForInvalidSubjectCommand
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSubjectEnrollmentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode

data class SelectReplacingSubjectForInvalidSubjectEvent(
    val id: StudentSubjectEnrollmentId,
    val subjectCode: SubjectCode
): AbstractEvent<StudentSubjectEnrollmentId>(id) {
    constructor(command: SelectReplacingSubjectForInvalidSubjectCommand) : this(
        command.subjectId,
        command.subjectCode
    )
}
