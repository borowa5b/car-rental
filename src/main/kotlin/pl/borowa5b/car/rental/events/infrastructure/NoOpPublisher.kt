package pl.borowa5b.car.rental.events.infrastructure

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.events.domain.model.ApplicationEvent

@Component
@ConditionalOnMissingBean(value = [RabbitMqQueuePublisher::class, KafkaQueuePublisher::class])
class NoOpPublisher : ExternalQueuePublisher {

    override fun publish(event: ApplicationEvent): Result<Unit> {
        return Result.failure(IllegalStateException("No event queue publisher configured, skipping event publication: ${event.type}"))
    }
}