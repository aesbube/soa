package mk.ukim.finki.studentsemesterenrollment.service.impl

import jakarta.persistence.EntityNotFoundException
import mk.ukim.finki.studentsemesterenrollment.model.StudentSemesterEnrollment
import mk.ukim.finki.studentsemesterenrollment.repository.jpaRepositories.StudentSemesterEnrollmentJpaRepository
import mk.ukim.finki.studentsemesterenrollment.service.StudentSemesterEnrollmentService
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class StudentSemesterEnrollmentServiceImpl(
    val repository: StudentSemesterEnrollmentJpaRepository
) : StudentSemesterEnrollmentService {
    override fun getAll(): List<StudentSemesterEnrollment> = repository.findAll()

    override fun getById(id: StudentSemesterEnrollmentId) = repository.findByIdOrNull(id) ?: throw EntityNotFoundException("Student semester enrollment with id ${id.id} not found")
}