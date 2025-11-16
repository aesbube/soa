package mk.ukim.finki.studentsemesterenrollment.handlers

import mk.ukim.finki.studentsemesterenrollment.commands.CreateStudentRecordCommand
import mk.ukim.finki.studentsemesterenrollment.valueObjects.ECTSCredits
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudyProgram
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

data class StudentCreatedEvent(
    val studentId: String,
    val studyProgram: StudyProgram
)
@Component
class StudentRecordEventHandler(
    private val commandGateway: CommandGateway
) {

    @EventHandler
    fun onStudentCreated(event: StudentCreatedEvent) {
        commandGateway.send<StudentId>(CreateStudentRecordCommand(
            id = StudentId(event.studentId),
            studyProgram = event.studyProgram,
            ects = ECTSCredits(0)
        ))
    }
}