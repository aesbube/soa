package mk.ukim.finki.studentsemesterenrollment.commands

import mk.ukim.finki.studentsemesterenrollment.valueObjects.Grade
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.LocalDate

data class SubjectExamCommand(
    @TargetAggregateIdentifier
    val studentId: StudentId,
    val subjectCode: SubjectCode,
    val grade: Grade,
    val externalId: Long,
    val professorId: Long,
    val datePassed: LocalDate,
)
