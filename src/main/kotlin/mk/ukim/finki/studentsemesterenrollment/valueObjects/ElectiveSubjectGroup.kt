package mk.ukim.finki.studentsemesterenrollment.valueObjects

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class ElectiveSubjectGroup private constructor(
    @Column(name="elective_subject_group")
    val value: String) {
    companion object {
        private val VALID_PATTERN = Regex("""^F\d{2}L\d[SW]$""")

        fun from(value: String): ElectiveSubjectGroup {
            require(VALID_PATTERN.matches(value)) {
                "Invalid ElectiveSubjectGroup format. Expected format: F##L#S or F##L#W"
            }
            return ElectiveSubjectGroup(value)
        }
    }

    override fun toString(): String = value
}