package mk.ukim.finki.studentsemesterenrollment.events

import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterId
import java.time.ZonedDateTime

data class SemesterCreatedEventData(
    val code: String,
    val year: String,
    val startDate: ZonedDateTime,
    val endDate: ZonedDateTime,
    val enrollmentStartDate: ZonedDateTime,
    val enrollmentEndDate: ZonedDateTime,
    val semesterType: String,
    val semesterCycle: String,
    val semesterStatus: String
)

data class SemesterCreatedEvent(
    val semesterId: SemesterId,
    val startDate: ZonedDateTime,
    val endDate: ZonedDateTime,
    val enrollmentStartDate: ZonedDateTime,
    val enrollmentEndDate: ZonedDateTime,
): AbstractEvent<SemesterId>(semesterId)