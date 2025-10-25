package mk.ukim.finki.studentsemesterenrollment.commands

import mk.ukim.finki.studentsemesterenrollment.valueObjects.ECTSCredits
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentId

data class CreateStudentRecordCommand(
    val id: StudentId,
    val ects: ECTSCredits
) {
    constructor(command: CreateStudentRecordCommand) : this (
        command.id,
        command.ects
    )
}