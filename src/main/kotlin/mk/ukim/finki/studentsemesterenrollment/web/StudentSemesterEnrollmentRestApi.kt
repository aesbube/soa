package mk.ukim.finki.studentsemesterenrollment.web

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mk.ukim.finki.studentsemesterenrollment.service.StudentSemesterEnrollmentService
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId
import mk.ukim.finki.studentsemesterenrollment.web.mapper.StudentSemesterEnrollmentMapper
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/student-semester-enrollment")
@Tag(
    name = "STUDENT_SEMESTER_ENROLLMENT_ENDPOINT",
    description = "Endpoint for the student semester enrollment"
)
class StudentSemesterEnrollmentRestApi(
    private val studentSemesterEnrollmentMapper: StudentSemesterEnrollmentMapper
) {
    @Operation(
        summary = "Fetching data for all StudentSemesterEnrollment",
    )
    @GetMapping("")
    fun getAll() = studentSemesterEnrollmentMapper.getAll()

    @GetMapping("{id}")
    fun getById(@PathVariable id: StudentSemesterEnrollmentId) = studentSemesterEnrollmentMapper.getById(id)
}