package mk.ukim.finki.studentsemesterenrollment.valueObjects

import jakarta.persistence.Column
import jakarta.persistence.Embeddable


@Embeddable
data class StudentSemesterEnrollmentId(
    @Column(name="student_semester_enrollment_id")
    val id: String) {
    init {
        require(id.length == 16 && id.matches(Regex("^\\d{4}-\\d{2}-(W|S)-\\d{6}$"))) {
            throw IllegalArgumentException("Invalid student semester enrollment id: $id")
        }
    }

    constructor(
        semesterCode: CycleSemesterId,
        studentIndex: StudentId
    ) : this("${semesterCode.value}-${studentIndex.index}")

    fun semesterCode(): SemesterId {
        return SemesterId(id.substring(0, 9))
    }

    fun studentIndex(): StudentId {
        return StudentId(id.substring(10, 16))
    }

    override fun toString(): String {
        return id
    }


}
