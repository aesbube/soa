package mk.ukim.finki.studentsemesterenrollment.valueObjects

import jakarta.persistence.Column
import jakarta.persistence.Embeddable


@Embeddable
data class StudentSubjectEnrollmentId(
    @Column(name = "student_enrollment_id")
    val value: String
) {
    constructor(semesterEnrollmentId: StudentSemesterEnrollmentId, subjectCode: SubjectCode) : this(
        "${semesterEnrollmentId.id}-${subjectCode.value}"
    ) {
    }

    override fun toString(): String {
        return value
    }

    fun subjectCode(): SubjectCode {
        return SubjectCode(value.split("-").last())
    }

    fun electiveSubjectGroup(): ElectiveSubjectGroup = ElectiveSubjectGroup.from(subjectCode().value.take(6))

    fun semesterEnrollmentId() = StudentSemesterEnrollmentId(value.take(18))

}
