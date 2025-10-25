package mk.ukim.finki.studentsemesterenrollment.handlers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.cloudevents.CloudEvent
import io.cloudevents.core.builder.CloudEventBuilder
import io.cloudevents.core.provider.EventFormatProvider
import mk.ukim.finki.studentsemesterenrollment.events.AbstractEvent
import mk.ukim.finki.studentsemesterenrollment.kafka.KafkaProducer
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component
import java.net.URI
import java.time.OffsetDateTime
import java.util.*

@Component
class EventMessagingEventHandler(val eventMessagingService: KafkaProducer) {
    @EventHandler
    fun <T> on(event: AbstractEvent<T>) {

        val objectMapper = ObjectMapper()
            .registerModule(JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

        val externalEvent = event.toExternalEvent() ?: return

        val cloudEvent: CloudEvent = CloudEventBuilder.v1()
            .withId(UUID.randomUUID().toString())
            .withSource(URI.create("finance-management-service"))
            .withType(event::class.qualifiedName ?: "UnknownType")
            .withTime(OffsetDateTime.now())
            .withDataContentType("application/json")
            .withData("application/json", objectMapper.writeValueAsBytes(externalEvent))
            .build()

        val format = EventFormatProvider.getInstance()
            .resolveFormat("application/cloudevents+json")
            ?: throw IllegalStateException("CloudEvent JSON format not available")

        val cloudEventJson = String(format.serialize(cloudEvent))

        eventMessagingService.send(
            topic = event.eventTopic(),
            key = event.identifier.toString(),
            payload = cloudEventJson
        )
    }
}