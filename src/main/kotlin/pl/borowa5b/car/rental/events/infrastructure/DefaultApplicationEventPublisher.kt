package pl.borowa5b.car.rental.events.infrastructure

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.events.domain.DomainEvent
import pl.borowa5b.car.rental.events.domain.generator.ApplicationEventIdGenerator
import pl.borowa5b.car.rental.events.domain.model.ApplicationEvent
import pl.borowa5b.car.rental.events.domain.repository.ApplicationEventRepository
import pl.borowa5b.car.rental.events.domain.shared.ApplicationEventPublisher
import pl.borowa5b.car.rental.events.domain.vo.ApplicationEventStatus
import java.util.logging.Logger

@Component
class DefaultApplicationEventPublisher(
    private val applicationEventRepository: ApplicationEventRepository,
    private val applicationEventIdGenerator: ApplicationEventIdGenerator,
    private val objectMapper: ObjectMapper
) : ApplicationEventPublisher {

    companion object {
        private val LOGGER = Logger.getLogger(DefaultApplicationEventPublisher::class.simpleName)
    }

    override fun publish(event: DomainEvent) {
        val payload = convertToPayload(event)
        val applicationEvent = ApplicationEvent(
            id = applicationEventIdGenerator.generate(),
            type = event.getType(),
            version = event.getVersion(),
            payload = payload,
            status = ApplicationEventStatus.NEW
        )
        applicationEventRepository.save(applicationEvent)
        // TODO: publish to external queue
    }

    private fun convertToPayload(event: DomainEvent): String? = try {
        objectMapper.writeValueAsString(event)
    } catch (_: JsonProcessingException) {
        LOGGER.warning(
            "Could not process application with type ${event.getType()} " +
                    "and version ${event.getVersion()} because it has invalid payload"
        )
        null
    }
}