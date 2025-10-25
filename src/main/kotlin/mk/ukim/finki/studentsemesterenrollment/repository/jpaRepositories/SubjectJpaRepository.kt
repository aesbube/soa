package mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories

import mk.ukim.finki.studentsemesterenrollment.aggregateSnapshot.SubjectAggregateSnapshot
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubjectJpaRepository : JpaRepository<SubjectAggregateSnapshot, SubjectCode> {
    fun findBySemesterCode(semesterCode: SemesterId): List<SubjectAggregateSnapshot>
    fun findAllByIdIn(ids: List<SubjectCode>): List<SubjectAggregateSnapshot>
}
