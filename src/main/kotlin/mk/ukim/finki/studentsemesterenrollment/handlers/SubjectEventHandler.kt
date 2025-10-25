package mk.ukim.finki.studentsemesterenrollment.handlers

import mk.ukim.finki.studentsemesterenrollment.aggregateSnapshot.SubjectAggregateSnapshot
import mk.ukim.finki.studentsemesterenrollment.events.CreateSubjectEvent
import mk.ukim.finki.studentsemesterenrollment.events.DeleteSubjectEvent
import mk.ukim.finki.studentsemesterenrollment.events.UpdateSubjectEvent
import mk.ukim.finki.studentsemesterenrollment.model.dto.SubjectEventData
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.SubjectJpaRepository
import mk.ukim.finki.studentsemesterenrollment.valueObjects.*
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class SubjectEventHandler(
    private val subjectRepository: SubjectJpaRepository
) {

    @EventHandler
    fun on(event: CreateSubjectEvent) {
        val subjectData = event.subjectData
        val subject = mapToAggregate(subjectData)
        subjectRepository.save(subject)
    }

    @EventHandler
    fun on(event: UpdateSubjectEvent) {
        val subjectData = event.subjectData
        val existing = subjectRepository.findById(event.id)
        if (existing.isPresent) {
            val subject = mapToAggregate(subjectData)
            subjectRepository.save(subject)
        } else {
            // If subject doesn't exist, you might want to throw an exception or log a warning
        }
    }

    @EventHandler
    fun on(event: DeleteSubjectEvent) {
        subjectRepository.deleteById(event.subjectCode)
    }

    private fun mapToAggregate(data: SubjectEventData): SubjectAggregateSnapshot {
        return SubjectAggregateSnapshot(
            id = SubjectCode(data.id),
            name = SubjectName(data.name),
            abbreviation = SubjectAbbreviation(data.abbreviation),
            semesterCode = SemesterId(data.semesterCode),
            ects = ECTSCredits(data.ects),
            semester = SemesterType.valueOf(data.semester),
            classesPerWeek = ClassesPerWeek(
                lectures = data.classesPerWeek.lectures,
                auditoriumClasses = data.classesPerWeek.auditoriumClasses,
                labClasses = data.classesPerWeek.labClasses
            )
        )
    }
}
