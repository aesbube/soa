package mk.ukim.finki.studentsemesterenrollment.model

import jakarta.persistence.*
import mk.ukim.finki.studentsemesterenrollment.valueObjects.ElectiveSubjectGroup
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectSlotId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectSlotStatus

@Entity
data class SubjectSlot(
    @EmbeddedId
    val id: SubjectSlotId,
    val subjectId: SubjectCode,
    @Embedded
    val electiveSubjectGroup: ElectiveSubjectGroup?,
    val status: SubjectSlotStatus,
    @Embedded
    val studentId: StudentId,
    @OneToOne(mappedBy = "subjectSlot")
    val exam: SubjectExam?,
    val mandatory: Boolean,
    val placeholder: Boolean,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SubjectSlot) return false

        return id == other.id &&
                subjectId == other.subjectId &&
                electiveSubjectGroup == other.electiveSubjectGroup &&
                status == other.status &&
                studentId == other.studentId &&
                exam == other.exam
    }

    override fun hashCode(): Int =
        id.hashCode() + subjectId.hashCode() + status.hashCode() + studentId.hashCode()
}
