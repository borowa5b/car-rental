package pl.borowa5b.car.rental.events.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import pl.borowa5b.car.rental.events.domain.model.ApplicationEvent

@Component
@ConditionalOnProperty(prefix = "car-rental", name = ["events-queue-provider"], havingValue = "kafka")
class KafkaQueuePublisher(
    @Value("\${spring.kafka.topic-id}") private val topicId: String,
    private val objectMapper: ObjectMapper,
    private val kafkaTemplate: KafkaTemplate<String, String>
) : ExternalQueuePublisher {

    @Transactional
    override fun publish(event: ApplicationEvent): Result<Unit> {
        return kafkaTemplate.send(topicId, objectMapper.writeValueAsString(event))
            .handle { _, exception ->
                if (exception == null) Result.success(Unit) else Result.failure(exception)
            }.join()
    }
}