package mk.ukim.finki.studentsemesterenrollment.listeners

import mk.ukim.finki.studentsemesterenrollment.events.CreateSubjectEvent
import mk.ukim.finki.studentsemesterenrollment.events.DeleteSubjectEvent
import mk.ukim.finki.studentsemesterenrollment.events.UpdateSubjectEvent
import mk.ukim.finki.studentsemesterenrollment.model.dto.SubjectEventData
import mk.ukim.finki.studentsemesterenrollment.valueObjects.SubjectCode
import org.axonframework.eventhandling.gateway.EventGateway
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class SubjectKafkaEventListener(
    private val eventGateway: EventGateway
) {

    private val logger = LoggerFactory.getLogger(SubjectKafkaEventListener::class.java)

    @KafkaListener(topics = ["accreditations.subjectCreated"], groupId = "subject-event-group")
    fun handleSubjectCreated(subjectEventData: SubjectEventData) {
        try {
            logger.info("Received subject created event: $subjectEventData")

            val event = CreateSubjectEvent(
                id = SubjectCode(subjectEventData.id),
                subjectData = subjectEventData
            )

            eventGateway.publish(event)

            logger.info("Successfully processed subject created event for subject: ${subjectEventData.id}")

        } catch (e: Exception) {
            logger.error("Error processing subject created event: ${e.message}", e)
        }
    }

    @KafkaListener(topics = ["accreditations.subjectUpdated"], groupId = "subject-event-group")
    fun handleSubjectUpdated(subjectEventData: SubjectEventData) {
        try {
            logger.info("Received subject updated event: $subjectEventData")

            val event = UpdateSubjectEvent(
                id = SubjectCode(subjectEventData.id),
                subjectData = subjectEventData
            )

            eventGateway.publish(event)

            logger.info("Successfully processed subject updated event for subject: ${subjectEventData.id}")

        } catch (e: Exception) {
            logger.error("Error processing subject updated event: ${e.message}", e)
        }
    }

    @KafkaListener(topics = ["accreditations.subjectDeleted"], groupId = "subject-event-group")
    fun handleSubjectDeleted(subjectEventData: SubjectEventData) {
        try {
            logger.info("Received subject deleted event: $subjectEventData")

            val event = DeleteSubjectEvent(
                id = SubjectCode(subjectEventData.id),
                subjectCode = SubjectCode(subjectEventData.id)
            )

            eventGateway.publish(event)

            logger.info("Successfully processed subject deleted event for subject: $subjectEventData.id")

        } catch (e: Exception) {
            logger.error("Error processing subject deleted event: ${e.message}", e)
        }
    }
}
