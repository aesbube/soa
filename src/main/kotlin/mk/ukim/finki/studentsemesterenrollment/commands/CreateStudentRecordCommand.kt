package mk.ukim.finki.studentsemesterenrollment.commands

import mk.ukim.finki.studentsemesterenrollment.valueObjects.ECTSCredits
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudyProgram
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class CreateStudentRecordCommand(
    @TargetAggregateIdentifier
    val id: StudentId,
    val ects: ECTSCredits,
    val studyProgram: StudyProgram
) {
    constructor(command: CreateStudentRecordCommand) : this (
        command.id,
        command.ects,
        command.studyProgram
    )
}