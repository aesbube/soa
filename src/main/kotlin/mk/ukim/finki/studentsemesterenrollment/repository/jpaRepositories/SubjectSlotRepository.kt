package mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories

import mk.ukim.finki.studentsemesterenrollment.model.SubjectSlot
import org.springframework.data.jpa.repository.JpaRepository

interface SubjectSlotRepository: JpaRepository<SubjectSlot, Long> {
}