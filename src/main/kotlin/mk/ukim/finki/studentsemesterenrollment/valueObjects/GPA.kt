package mk.ukim.finki.studentsemesterenrollment.valueObjects

import jakarta.persistence.Embeddable

@Embeddable
data class GPA(val gpa: Double) {
    init {
        require(gpa in 5.0..10.0) { "GPA value must be between 5.0 and 10.0" }
    }
}
