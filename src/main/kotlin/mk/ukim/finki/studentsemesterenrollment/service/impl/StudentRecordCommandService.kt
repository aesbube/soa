package mk.ukim.finki.studentsemesterenrollment.service.impl

import mk.ukim.finki.studentsemesterenrollment.commands.SubjectExamCommand
import mk.ukim.finki.studentsemesterenrollment.valueObjects.Grade
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class StudentRecordCommandService(
    private val commandGateway: CommandGateway,
) {
    fun subjectPassed(subjectCode: String, studentId: String, grade: Grade) {
        commandGateway.sendAndWait<Any>(
            SubjectExamCommand(
                studentId = StudentId(studentId),
                subjectCode = SubjectCode(subjectCode),
                grade = grade,
                externalId = 0L,
                professorId = 0L,
                datePassed = LocalDate.now(),
            )
        )
    }
}