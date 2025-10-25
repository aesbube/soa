package mk.ukim.finki.studentsemesterenrollment.valueObjects

import jakarta.persistence.Embeddable


@Embeddable
data class ClassesPerWeek(
    val lectures: Int,
    val auditoriumClasses: Int,
    val labClasses: Int
) {
    init {
        require(lectures in 0..4 && auditoriumClasses in 0..4 && labClasses in 0..4) {
            throw IllegalArgumentException("Invalid classes per week: $lectures lectures, $auditoriumClasses auditorium classes, $labClasses lab classes")
        }
    }

    constructor(lectureFond: String) : this(
        lectureFond.substringBefore('+').toInt(),
        lectureFond.substringAfter('+').substringBefore('+').toInt(),
        lectureFond.substringAfter('+').substringAfter('+').toInt()
    )

    override fun toString(): String {
        return "$lectures+${auditoriumClasses}+${labClasses}"
    }
}