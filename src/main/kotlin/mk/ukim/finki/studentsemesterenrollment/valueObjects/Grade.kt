package mk.ukim.finki.studentsemesterenrollment.valueObjects

import jakarta.persistence.Embeddable

@Embeddable
data class Grade(val grade: Int) {
    init {
        require(grade in 5..10) { "Grade must be between 5 and 10" }
    }
}