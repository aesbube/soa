package mk.ukim.finki.studentsemesterenrollment.events

import mk.ukim.finki.studentsemesterenrollment.commands.CreateStudentRecordCommand
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentId

data class StudentRecordCreatedEvent(
        val id: StudentId
) {
    constructor(
            command: CreateStudentRecordCommand
    ) : this (
            command.id
    )
}
