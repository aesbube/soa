package mk.ukim.finki.studentsemesterenrollment.model

import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import mk.ukim.finki.studentsemesterenrollment.aggregateSnapshot.SubjectAggregateSnapshot
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSubjectEnrollmentId

@Entity
class StudentSubjectEnrollment(
    @EmbeddedId
    private val studentSubjectEnrollmentId: StudentSubjectEnrollmentId,

    @ManyToOne
    private val subject: SubjectAggregateSnapshot,

    private val valid: Boolean
) {
    @ManyToOne
    private val replacingSubject: SubjectAggregateSnapshot? = null

    fun getId() = this.studentSubjectEnrollmentId
}
