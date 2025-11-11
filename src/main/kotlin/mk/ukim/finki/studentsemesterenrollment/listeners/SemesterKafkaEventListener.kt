package mk.ukim.finki.studentsemesterenrollment.listeners

import mk.ukim.finki.studentsemesterenrollment.config.loggerFor
import mk.ukim.finki.studentsemesterenrollment.events.SemesterCreatedEvent
import mk.ukim.finki.studentsemesterenrollment.events.SemesterCreatedEventData
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SemesterId
import org.axonframework.eventhandling.gateway.EventGateway
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class SemesterEventKafkaListener(
    private val eventGateway: EventGateway
) {

    private val logger = loggerFor<SemesterEventKafkaListener>()

    @KafkaListener(
        topics = ["semester-events"],
        groupId = "student-enrollment-service"
    )
    fun onSemesterCreated(message: SemesterCreatedEventData) {
        try {

            val internalEvent = SemesterCreatedEvent(
                semesterId = SemesterId("${message.startDate.year}=${message.endDate.year.toString().substring(2,4)}-${message.semesterType}"),
                enrollmentStartDate = message.enrollmentStartDate,
                enrollmentEndDate = message.enrollmentEndDate,
                startDate = message.startDate,
                endDate = message.endDate,
            )

            eventGateway.publish(internalEvent)
            logger.info("Published SemesterCreatedEvent to Axon: $internalEvent")

        } catch (e: Exception) {
            logger.error("Failed to process SemesterCreatedEvent from Kafka", e)
        }
    }
}