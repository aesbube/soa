package mk.ukim.finki.studentsemesterenrollment.aggregateSnapshot

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import mk.ukim.finki.studentsemesterenrollment.valueObjects.CycleSemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterState
import java.time.LocalDateTime

@Entity
@Table(name = "semesters")
class SemesterSnapshot (
    @EmbeddedId
    val id: SemesterId,
    val cycleSemesterId: CycleSemesterId,
    val state: SemesterState,
    val enrollmentStartDate: LocalDateTime,
    val enrollmentEndDate: LocalDateTime,
)