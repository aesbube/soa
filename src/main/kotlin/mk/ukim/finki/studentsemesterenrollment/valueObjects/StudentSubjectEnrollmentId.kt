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


}
