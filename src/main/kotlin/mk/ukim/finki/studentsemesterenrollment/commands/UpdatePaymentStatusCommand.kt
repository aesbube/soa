package mk.ukim.finki.studentsemesterenrollment.commands

import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId

data class UpdatePaymentStatusCommand(
    val semesterEnrollmentId: StudentSemesterEnrollmentId
)
