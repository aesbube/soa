package mk.ukim.finki.studentsemesterenrollment.events

import mk.ukim.finki.studentsemesterenrollment.model.dto.SubjectEventData
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode

data class CreateSubjectEvent(
    val id: SubjectCode,
    val subjectData: SubjectEventData
) : AbstractEvent<SubjectCode>(id) {

    override fun toExternalEvent(): SubjectEventData? = subjectData
}
