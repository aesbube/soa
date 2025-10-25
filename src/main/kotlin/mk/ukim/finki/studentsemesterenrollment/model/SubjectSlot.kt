package mk.ukim.finki.studentsemesterenrollment.model

import jakarta.persistence.*
import mk.ukim.finki.studentsemesterenrollment.valueObjects.ElectiveSubjectGroup
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectSlotStatus

@Entity
data class SubjectSlot(
    @Id
    val id: Long,
    val subjectId: SubjectCode,
    @Embedded
    val electiveSubjectGroup: ElectiveSubjectGroup ?,
    val status: SubjectSlotStatus,
    @ManyToOne(fetch = FetchType.LAZY)
    val student: StudentRecord,
    @OneToOne(mappedBy = "subjectSlot")
    val exam: SubjectExam ?
)