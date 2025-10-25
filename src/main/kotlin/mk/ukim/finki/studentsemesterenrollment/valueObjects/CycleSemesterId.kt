package mk.ukim.finki.studentsemesterenrollment.valueObjects

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class CycleSemesterId(
    @Column(name="cycle_semester_id")
    val value: String
) {


    constructor(semesterId: SemesterId,
                cycle: StudyCycle) : this("${semesterId.value}-${cycle.number}") {}

    override fun toString(): String {
        return value
    }
//
//    init {
//        require(cycle.number in 1..3) { "Cycle must be a positive number" }
//    }


}