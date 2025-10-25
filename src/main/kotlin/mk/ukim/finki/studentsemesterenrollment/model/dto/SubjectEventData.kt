package mk.ukim.finki.studentsemesterenrollment.model.dto

data class SubjectEventData(
    val id: String,
    val name: String,
    val abbreviation: String,
    val semesterCode: String,
    val ects: Int,
    val semester: String,
    val classesPerWeek: ClassesPerWeekData
)
