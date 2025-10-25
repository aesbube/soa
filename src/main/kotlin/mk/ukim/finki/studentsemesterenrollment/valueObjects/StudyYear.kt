package mk.ukim.finki.studentsemesterenrollment.valueObjects

import jakarta.persistence.Embeddable
import java.time.LocalDate

@Embeddable
data class StudyYear(val studyYear: String) {
    init {
        require(studyYear.length == 7 && studyYear.matches(Regex("^\\d{4}-\\d{2}$"))) {
        }
    }


    fun yearsPassed(date: LocalDate = LocalDate.now()): Int {
        val year = studyYear.split("-")[0].toInt()
        return date.year - year
    }
}
