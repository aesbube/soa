package mk.ukim.finki.studentsemesterenrollment.commands

import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentId

data class InitiateIrregularEnrollmentCommand
    (
    val studentId: StudentId,
    val semesterId: SemesterId
) {
    constructor(command: InitiateIrregularEnrollmentCommand) : this(
        studentId = command.studentId,
        semesterId = command.semesterId
    )
}