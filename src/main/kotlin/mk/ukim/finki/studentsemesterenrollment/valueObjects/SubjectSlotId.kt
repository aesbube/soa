package mk.ukim.finki.studentsemesterenrollment.valueObjects

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class SubjectSlotId(
    @Column(name ="subject_slot_id") val id: String
) {
    constructor(
        subjectId: SubjectCode,
        studentIndex: StudentId
    ) : this("${subjectId.value}-${studentIndex.index}")

    fun subjectId() = SubjectCode(this.id.split("-").first())
    fun studentId() = StudentId(this.id.split("-").last())

}