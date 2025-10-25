package mk.ukim.finki.studentsemesterenrollment.commands

import mk.ukim.finki.studentsemesterenrollment.valueObjects.CycleSemesterId

data class ExtendSemesterEnrollmentCommand (
    val cycleSemesterId: CycleSemesterId
){
    constructor(command: ExtendSemesterEnrollmentCommand) : this(
        cycleSemesterId = command.cycleSemesterId
    )
}