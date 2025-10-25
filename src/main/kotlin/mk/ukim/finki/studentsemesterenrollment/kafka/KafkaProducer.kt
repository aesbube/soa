package mk.ukim.finki.studentsemesterenrollment.kafka

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaProducer(private val kafkaTemplate: KafkaTemplate<String, String>) {
    fun send(topic: String, key: String, payload: String) {
        kafkaTemplate.send(topic, key, payload)
    }
}
