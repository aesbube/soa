package mk.ukim.finki.studentsemesterenrollment.web.mapper

import mk.ukim.finki.studentsemesterenrollment.service.StudentSemesterEnrollmentService
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId
import org.springframework.stereotype.Service

@Service
class StudentSemesterEnrollmentMapper(
    val studentSemesterEnrollmentService: StudentSemesterEnrollmentService
) {
    fun getAll() = studentSemesterEnrollmentService.getAll().map { it.toDto() }
    fun getById(id: StudentSemesterEnrollmentId) = studentSemesterEnrollmentService.getById(id).toDto()
}


