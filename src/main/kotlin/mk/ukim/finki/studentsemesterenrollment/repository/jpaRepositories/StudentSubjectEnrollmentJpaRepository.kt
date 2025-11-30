package mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories

import mk.ukim.finki.studentsemesterenrollment.model.StudentSubjectEnrollment
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSubjectEnrollmentId
import org.springframework.data.jpa.repository.JpaRepository

interface StudentSubjectEnrollmentJpaRepository : JpaRepository<StudentSubjectEnrollment, StudentSubjectEnrollmentId> {
    fun findAllByStudentSubjectEnrollmentIdIn(studentSubjectEnrollmentIds: List<StudentSubjectEnrollmentId>): List<StudentSubjectEnrollment>
}