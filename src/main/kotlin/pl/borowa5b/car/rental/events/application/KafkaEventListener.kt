package pl.borowa5b.car.rental.events.application

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.events.domain.model.ExternalEvent
import pl.borowa5b.car.rental.events.infrastructure.ExternalEventProcessor

@Component
@ConditionalOnProperty(prefix = "car-rental", name = ["events-queue-provider"], havingValue = "kafka")
class KafkaEventListener(
    private val objectMapper: ObjectMapper,
    private val externalEventProcessor: ExternalEventProcessor
) {

    @KafkaListener(topics = ["\${spring.kafka.topic-id}"])
    fun handle(eventMessage: String) {
        val externalEvent = objectMapper.readValue(eventMessage, ExternalEvent::class.java)
        externalEventProcessor.process(externalEvent)
    }
}