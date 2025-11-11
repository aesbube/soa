package mk.ukim.finki.studentsemesterenrollment.client

import mk.ukim.finki.studentsemesterenrollment.client.fallbacks.SemesterClientFallback
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterState
import mk.ukim.finki.studentsemesterenrollment.valueObjects.StudyCycle
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.time.ZonedDateTime

@FeignClient(
    name = "semester-service",
    url = "/semester/mock",
    fallback = SemesterClientFallback::class
)
interface SemesterClient {

    @GetMapping("/api/semesters/{semesterId}")
    fun getSemesterById(@PathVariable semesterId: String): SemesterDto
}

data class SemesterDto(
    val semesterId: SemesterId,
    val cycleId: StudyCycle,
    val state: SemesterState,
    val enrollmentStartDate: ZonedDateTime,
    val enrollmentEndDate: ZonedDateTime,
)