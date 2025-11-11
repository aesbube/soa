package mk.ukim.finki.studentsemesterenrollment.valueObjects

import jakarta.persistence.Column
import jakarta.persistence.Embeddable


@Embeddable
data class StudentSemesterEnrollmentId(
    @Column(name="student_semester_enrollment_id")
    val id: String) {
    init {
        require(id.length == 18) {
            throw IllegalArgumentException("Invalid student semester enrollment id: $id. Length must be 18 chars.")
        }
        require(id.matches(Regex("^\\d{4}-\\d{2}-(W|S)-\\d-\\d{6}$"))) {
            throw IllegalArgumentException("Invalid student semester enrollment id: $id. Must be in format [yyyy-yy-(W|S)-CID-INDEX]")
        }
    }

    constructor(
        semesterCode: CycleSemesterId,
        studentIndex: StudentId
    ) : this("${semesterCode.value}-${studentIndex.index}")

    fun semesterCode(): CycleSemesterId {
        return CycleSemesterId(id.substring(0, 9))
    }

    fun studentIndex(): StudentId {
        return StudentId(id.split("-").last())
    }

    override fun toString(): String {
        return id
    }


}
