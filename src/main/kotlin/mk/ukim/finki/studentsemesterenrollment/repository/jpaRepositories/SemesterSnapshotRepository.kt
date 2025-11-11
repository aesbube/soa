package mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories

import mk.ukim.finki.studentsemesterenrollment.aggregateSnapshot.SemesterSnapshot
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterId
import org.springframework.data.jpa.repository.JpaRepository

interface SemesterSnapshotRepository: JpaRepository<SemesterSnapshot, SemesterId> {
}