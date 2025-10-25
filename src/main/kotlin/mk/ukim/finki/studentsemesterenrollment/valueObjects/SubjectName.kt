package mk.ukim.finki.studentsemesterenrollment.valueObjects

import jakarta.persistence.Embeddable


@Embeddable
data class SubjectName(val name: String) {
    init {
        require(name.length in 4..254) {
            throw IllegalArgumentException("Invalid subject name length: $name. It should be between 3 and 255 characters")
        }
        // it should be only with latin or cyrillic letters, numbers, and single spaces
        require(name.matches(Regex("^[a-zA-Zа-шА-Ш0-9 ]+$"))) {
            throw IllegalArgumentException("Invalid subject name: $name. It should contain only latin or cyrillic letters, numbers, and single spaces")
        }
    }
}