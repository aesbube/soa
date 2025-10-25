package mk.ukim.finki.studentsemesterenrollment.events

import mk.ukim.finki.studentsemesterenrollment.commands.ProvisionallyEnrollStudentOnSubjectCommand
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode

data class StudentProvisionallyEnrolledOnSubjectEvent(
    val studentSemesterEnrollmentId: StudentSemesterEnrollmentId,
    val subjectCode: SubjectCode
) : AbstractEvent<StudentSemesterEnrollmentId>(studentSemesterEnrollmentId) {
    constructor(command: ProvisionallyEnrollStudentOnSubjectCommand) : this(
        studentSemesterEnrollmentId = command.studentSemesterEnrollmentId,
        subjectCode = command.subjectCode,
    )
}