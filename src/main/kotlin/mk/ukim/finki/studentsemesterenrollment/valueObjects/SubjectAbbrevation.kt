package mk.ukim.finki.studentsemesterenrollment.valueObjects

import jakarta.persistence.Embeddable

@Embeddable
data class SubjectAbbreviation(val abbreviation: String) {
    init {
        require(abbreviation.length in 2..10) {
            throw IllegalArgumentException("Invalid subject abbreviation length: $abbreviation. It should be between 2 and 10 characters")
        }
        // it should be only with latin uppercase letters and numbers
        require(abbreviation.matches(Regex("^[A-Z0-9]+$"))) {
            throw IllegalArgumentException("Invalid subject abbreviation: $abbreviation. It should contain only latin uppercase letters and numbers")
        }
    }
}
