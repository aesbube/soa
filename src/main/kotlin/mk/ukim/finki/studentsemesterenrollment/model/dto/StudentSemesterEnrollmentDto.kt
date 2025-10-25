package mk.ukim.finki.studentsemesterenrollment.model.dto

import mk.ukim.finki.studentsemesterenrollment.model.StudentSemesterEnrollment
import mk.ukim.finki.studentsemesterenrollment.valueObjects.CycleSemesterId
import org.apache.kafka.common.protocol.types.Field.Str
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDate
import java.time.LocalDateTime

data class StudentSemesterEnrollmentDto(
    val index: String,
    val cycleSemesterId: String,
    val lastModifiedDate: LocalDateTime,
    val createdDate: LocalDateTime,
)