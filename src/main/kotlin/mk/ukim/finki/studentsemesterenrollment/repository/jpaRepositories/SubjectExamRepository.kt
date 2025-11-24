package mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories

import mk.ukim.finki.studentsemesterenrollment.model.SubjectExam
import org.springframework.data.jpa.repository.JpaRepository

interface SubjectExamRepository: JpaRepository<SubjectExam, Long> {
}