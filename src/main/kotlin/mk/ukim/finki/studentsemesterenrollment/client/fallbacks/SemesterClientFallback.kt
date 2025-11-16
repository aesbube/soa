package mk.ukim.finki.studentsemesterenrollment.client.fallbacks

import mk.ukim.finki.studentsemesterenrollment.client.SemesterClient
import mk.ukim.finki.studentsemesterenrollment.client.SemesterDto
import mk.ukim.finki.studentsemesterenrollment.config.Constants
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterState
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudyCycle
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class SemesterClientFallback: SemesterClient {
    override fun getSemesterById(
        semesterId: String
    ): SemesterDto {
        return Constants.semesters.find { it.semesterId == SemesterId(semesterId) } ?: throw RuntimeException("Semester not found")
    }
}