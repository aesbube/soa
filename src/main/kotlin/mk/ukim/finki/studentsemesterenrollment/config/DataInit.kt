package mk.ukim.finki.studentsemesterenrollment.config

import mk.ukim.finki.studentsemesterenrollment.events.CreateSubjectEvent
import mk.ukim.finki.studentsemesterenrollment.events.SemesterCreatedEvent
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterId
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode
import org.axonframework.eventhandling.gateway.EventGateway
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class DataInit(
    private val eventGateway: EventGateway
) {
    init {
        // init subjects

    }
}