package mk.ukim.finki.studentsemesterenrollment.events

import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode

data class DeleteSubjectEvent(
    val id: SubjectCode,
    val subjectCode: SubjectCode
) : AbstractEvent<SubjectCode>(id) {

    override fun toExternalEvent(): SubjectCode? = subjectCode
}