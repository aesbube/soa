package mk.ukim.finki.studentsemesterenrollment.service

import mk.ukim.finki.studentsemesterenrollment.model.StudentSemesterEnrollment
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId

interface StudentSemesterEnrollmentService {
    fun getAll() : List<StudentSemesterEnrollment>
    fun getById(id : StudentSemesterEnrollmentId) : StudentSemesterEnrollment
}