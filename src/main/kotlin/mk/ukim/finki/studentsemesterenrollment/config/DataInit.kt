package mk.ukim.finki.studentsemesterenrollment.config

import mk.ukim.finki.studentsemesterenrollment.aggregateSnapshot.SemesterSnapshot
import mk.ukim.finki.studentsemesterenrollment.aggregateSnapshot.SubjectAggregateSnapshot
import mk.ukim.finki.studentsemesterenrollment.commands.CreateStudentRecordCommand
import mk.ukim.finki.studentsemesterenrollment.events.CreateSubjectEvent
import mk.ukim.finki.studentsemesterenrollment.events.SemesterCreatedEvent
import mk.ukim.finki.studentsemesterenrollment.model.StudentRecord
import mk.ukim.finki.studentsemesterenrollment.model.SubjectSlot
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SemesterSnapshotRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.StudentRecordJpaRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SubjectJpaRepository
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SubjectSlotRepository
import mk.ukim.finki.studentsemesterenrollment.valueObjects.ClassesPerWeek
import mk.ukim.finki.studentsemesterenrollment.valueObjects.CycleSemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.ECTSCredits
import mk.ukim.finki.studentsemesterenrollment.valueObjects.GPA
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterState
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterType
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudyCycle
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudyProgram
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudyYear
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectAbbreviation
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectName
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectSlotStatus
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventhandling.gateway.EventGateway
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZonedDateTime

@Component
class DataInit(
    private val eventGateway: EventGateway,
    private val subjectSnapshotRepository: SubjectJpaRepository,
    private val semesterSnapshotRepository: SemesterSnapshotRepository,
    private val studentRecordRepository: StudentRecordJpaRepository,
    private val subjectSlotRepository: SubjectSlotRepository,
    private val commandGateway: CommandGateway,
): ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        Constants.subjects.map { subjectData ->
            SubjectAggregateSnapshot(
                id = SubjectCode(subjectData.id),
                name = SubjectName(subjectData.name),
                abbreviation = SubjectAbbreviation(subjectData.abbreviation),
                semesterCode = SemesterId(subjectData.semesterCode),
                ects = ECTSCredits(subjectData.ects),
                semester = SemesterType.valueOf(subjectData.semester),
                classesPerWeek = ClassesPerWeek(
                    lectures = subjectData.classesPerWeek.lectures,
                    auditoriumClasses = subjectData.classesPerWeek.auditoriumClasses,
                    labClasses = subjectData.classesPerWeek.labClasses
                )
            )
        }.let {
            subjectSnapshotRepository.saveAllAndFlush(it)
        }

        val semester = SemesterSnapshot(
            id = SemesterId("2021-22-W"),
            cycleSemesterId = CycleSemesterId(SemesterId("2021-22-W"), StudyCycle.UNDERGRADUATE),
            state = SemesterState.STUDENTS_ENROLLMENT,
            enrollmentStartDate = ZonedDateTime.now().toLocalDateTime(),
            enrollmentEndDate = ZonedDateTime.now().plusDays(21).toLocalDateTime()
        )
        semesterSnapshotRepository.saveAndFlush(semester)
        val semester2 = SemesterSnapshot(
            id = SemesterId("2021-22-S"),
            cycleSemesterId = CycleSemesterId(SemesterId("2021-22-S"), StudyCycle.UNDERGRADUATE),
            state = SemesterState.STUDENTS_ENROLLMENT,
            enrollmentStartDate = ZonedDateTime.now().toLocalDateTime(),
            enrollmentEndDate = ZonedDateTime.now().plusDays(21).toLocalDateTime()
        )
        semesterSnapshotRepository.saveAndFlush(semester2)

        val studentRecord = commandGateway.sendAndWait<StudentId>(CreateStudentRecordCommand(
            studyProgram = StudyProgram.Type.COMPUTER_SCIENCE.toStudyProgram(),
            id = StudentId("216049"),
            ects = ECTSCredits(0)
        ))
    }
}