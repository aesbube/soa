package mk.ukim.finki.studentsemesterenrollment.valueObjects

import jakarta.persistence.Column
import jakarta.persistence.Embeddable


@Embeddable
data class SemesterId(
    @Column(name="semester_code")
    val value: String) {
    init {
        require(Regex("""^\d{4}-\d{2}-[WS]$""").matches(value)) {
            "Invalid SemesterId format. The format should be YYYY-YY-W/S (2024-25-W or 2024-25-S)"
        }
    }

    val startYear: Int get() = value.substring(0, 4).toInt()
    val endYear: Int get() = "20${value.substring(5, 7)}".toInt()
    val isWinterSemester: Boolean get() = value.endsWith("W")
    val isSummerSemester: Boolean get() = value.endsWith("S")

    override fun toString(): String {
        return value
    }
}
