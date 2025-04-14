package pl.borowa5b.car.rental.events.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import pl.borowa5b.car.rental.events.domain.model.ApplicationEvent

@Component
@ConditionalOnProperty(prefix = "car-rental", name = ["events-queue-provider"], havingValue = "rabbitmq")
class RabbitMqQueuePublisher(
    @Value("\${spring.rabbitmq.exchange}") private val exchange: String,
    @Value("\${spring.rabbitmq.routing-key}") private val routingKey: String,
    private val objectMapper: ObjectMapper,
    private val rabbitTemplate: RabbitTemplate
) : ExternalQueuePublisher {

    @Transactional
    override fun publish(event: ApplicationEvent): Result<Unit> =
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, objectMapper.writeValueAsString(event))
            Result.success(Unit)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
}