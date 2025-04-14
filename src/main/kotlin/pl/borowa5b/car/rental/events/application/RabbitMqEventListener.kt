package pl.borowa5b.car.rental.events.application

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.events.domain.model.ExternalEvent
import pl.borowa5b.car.rental.events.infrastructure.ExternalEventProcessor

@Component
@ConditionalOnProperty(prefix = "car-rental", name = ["events-queue-provider"], havingValue = "rabbitmq")
class RabbitMqEventListener(
    private val objectMapper: ObjectMapper,
    private val externalEventProcessor: ExternalEventProcessor
) {

    @RabbitListener(id = "event-listener", queues = ["\${spring.rabbitmq.queue}"])
    fun handle(eventMessage: String) {
        val externalEvent = objectMapper.readValue(eventMessage, ExternalEvent::class.java)
        externalEventProcessor.process(externalEvent)
    }
}