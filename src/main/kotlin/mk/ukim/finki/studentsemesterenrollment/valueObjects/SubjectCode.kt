package mk.ukim.finki.studentsemesterenrollment.valueObjects

import jakarta.persistence.Embeddable


@Embeddable
data class SubjectCode(val value: String) {
    init {
        val pattern = Regex("""^(F\d{2}L\d[WS]\d{3}|CSE[WS]\d{3})$""")
        require(pattern.matches(value)) {
            "Invalid SubjectCode format. Expected FXXLY(W/S)ZZZ or CSE(W/S)ZZZ (F23L3W101 or CSEW101). Found: $value"
        }
    }

    val isNewAccreditaion: Boolean get() = value.startsWith("F") // new accreditation
    val isOldAccreditaion: Boolean get() = value.startsWith("CSE") //old accreditation ()

    override fun toString(): String {
        return value
    }
}
