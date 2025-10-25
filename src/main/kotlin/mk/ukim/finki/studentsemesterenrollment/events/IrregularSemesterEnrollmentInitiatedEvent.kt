package mk.ukim.finki.studentsemesterenrollment.events

import mk.ukim.finki.studentsemesterenrollment.commands.InitiateIrregularEnrollmentCommand
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentId

data class IrregularSemesterEnrollmentInitiatedEvent(
    val studentId: StudentId,
    val semesterId: SemesterId
) : AbstractEvent<StudentId>(studentId) {
    constructor(command: InitiateIrregularEnrollmentCommand) : this(
        command.studentId,
        command.semesterId

    )
}