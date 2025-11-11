package mk.ukim.finki.studentsemesterenrollment.commands

import mk.ukim.finki.studentsemesterenrollment.valueObjects.CycleSemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class StartRegularEnrollmentCommand(
    @TargetAggregateIdentifier
    val studentSemesterEnrollmentId: StudentSemesterEnrollmentId
)
