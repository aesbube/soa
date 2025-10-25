package mk.ukim.finki.studentsemesterenrollment.model

import jakarta.persistence.*
import mk.ukim.finki.studentsemesterenrollment.valueObjects.Grade
import java.time.LocalDate

@Entity
class SubjectExam (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val professor: Long,
    val grade: Grade,
    val datePassed: LocalDate,
    val externalId: Long,
    @OneToOne(fetch = FetchType.LAZY)
    val subjectSlot: SubjectSlot
) {
}