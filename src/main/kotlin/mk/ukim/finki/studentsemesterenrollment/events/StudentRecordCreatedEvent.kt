package mk.ukim.finki.studentsemesterenrollment.events

import mk.ukim.finki.studentsemesterenrollment.commands.CreateStudentRecordCommand
import mk.ukim.finki.studentsemesterenrollment.valueObjects.ECTSCredits
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudyProgram
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode

data class StudentRecordCreatedEvent(
    val id: StudentId,
    val ects: ECTSCredits,
    val studyProgram: StudyProgram,
    val subjects: List<SubjectCode>
) {
    constructor(command: CreateStudentRecordCommand) : this(
        id = command.id,
        ects = command.ects,
        studyProgram = command.studyProgram,
        subjects = emptyList()
    )

    constructor(
        command: CreateStudentRecordCommand,
        subjects: List<SubjectCode>
    ) : this(
        id = command.id,
        ects = command.ects,
        studyProgram = command.studyProgram,
        subjects = subjects
    )
}