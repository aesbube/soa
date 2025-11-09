package mk.ukim.finki.studentsemesterenrollment.web

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mk.ukim.finki.studentsemesterenrollment.service.StudentSemesterEnrollmentService
import mk.ukim.finki.studentsemesterenrollment.service.impl.StudentSemesterEnrollmentCommandService
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudentSemesterEnrollmentId
import mk.ukim.finki.studentsemesterenrollment.web.mapper.StudentSemesterEnrollmentMapper
import mk.ukim.finki.studentsemesterenrollment.web.request.ProvisionallyEnrollStudentOnSubjectRequest
import mk.ukim.finki.studentsemesterenrollment.web.request.StartEnrollmentRequest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/student-semester-enrollment")
@Tag(
    name = "STUDENT_SEMESTER_ENROLLMENT_ENDPOINT",
    description = "Endpoint for the student semester enrollment"
)
class StudentSemesterEnrollmentRestApi(
    private val studentSemesterEnrollmentMapper: StudentSemesterEnrollmentMapper,
    private val studentSemesterEnrollmentCommandService: StudentSemesterEnrollmentCommandService,
) {
    @Operation(
        summary = "Fetching data for all StudentSemesterEnrollment",
    )
    @GetMapping("")
    fun getAll() = studentSemesterEnrollmentMapper.getAll()

    @GetMapping("{id}")
    fun getById(@PathVariable id: StudentSemesterEnrollmentId) = studentSemesterEnrollmentMapper.getById(id)

    @PostMapping
    fun startEnrollment(@RequestBody request: StartEnrollmentRequest) = studentSemesterEnrollmentCommandService
        .startEnrollment(
            studentIndex = request.studentIndex,
            cycleId = request.cycleId,
            semesterId = request.semesterId,
        )

    @PutMapping("/{id}/{subjectCode}")
    fun provisionallyEnrollStudentOnSubject(
        @PathVariable id: String,
        @PathVariable subjectCode: String,
    ) =
        studentSemesterEnrollmentCommandService.provisionallyEnrollStudentOnSubject(
            id = id,
            subjectCode = subjectCode,
        )

    @PutMapping("/{id}/validate")
    fun validateStudentEnrollment(@PathVariable id: String) = studentSemesterEnrollmentCommandService
        .validateEnrollmentConditions(id)

    @PutMapping("/{id}/update-payment")
    fun updatePayment(@PathVariable id: String) = studentSemesterEnrollmentCommandService.updatePaymentStatus(id)
}