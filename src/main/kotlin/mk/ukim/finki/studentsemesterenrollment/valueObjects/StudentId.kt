package mk.ukim.finki.studentsemesterenrollment.valueObjects

import jakarta.persistence.Embeddable


@Embeddable
data class StudentId (
    val index: String) {
    init {
        // TODO: Mozebi format za indeks
        require(index.length == 6) { "Index must be 6 digits" }
        require(index.toCharArray().all { it.isDigit() }) { "Index must be an Int" }
    }

    fun enrollmentYear(): StudyYear {
        val year = index.substring(0, 2)
        val nextYear = (year.toInt() + 1).toString()
        return StudyYear("20${year}-${nextYear}")
    }

    override fun toString(): String {
        return index
    }
}