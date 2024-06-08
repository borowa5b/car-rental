package pl.borowa5b.car.rental.infrastructure.event

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.domain.ApplicationEventPublisher
import pl.borowa5b.car.rental.domain.generator.ApplicationEventIdGenerator
import pl.borowa5b.car.rental.domain.model.ApplicationEvent
import pl.borowa5b.car.rental.domain.model.DomainEvent
import pl.borowa5b.car.rental.domain.model.vo.ApplicationEventStatus
import pl.borowa5b.car.rental.domain.repository.ApplicationEventRepository
import java.util.logging.Logger

@Component
class DefaultApplicationEventPublisher(
    private val applicationEventRepository: ApplicationEventRepository,
    private val applicationEventIdGenerator: ApplicationEventIdGenerator,
    private val objectMapper: ObjectMapper
) : ApplicationEventPublisher {

    private val logger = Logger.getLogger(DefaultApplicationEventPublisher::class.simpleName)

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
    } catch (exception: JsonProcessingException) {
        logger.warning(
            "Could not process application with type ${event.getType()} " +
                    "and version ${event.getVersion()} because it has invalid payload"
        )
        null
    }
}