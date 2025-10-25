package mk.ukim.finki.studentsemesterenrollment.events

import mk.ukim.finki.studentsemesterenrollment.commands.ExtendSemesterEnrollmentCommand
import mk.ukim.finki.studentsemesterenrollment.valueObjects.CycleSemesterId

class CycleSemesterEnrollmentEndDateUpdatedEvent (
    val cycleSemesterId: CycleSemesterId
) : AbstractEvent<CycleSemesterId>(cycleSemesterId) {
    constructor(command: ExtendSemesterEnrollmentCommand) : this(
        cycleSemesterId = command.cycleSemesterId
    )
}