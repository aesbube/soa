package mk.ukim.finki.studentsemesterenrollment.valueObjects

import jakarta.persistence.Embeddable

@Embeddable
data class ECTSCredits (val credits: Int)
