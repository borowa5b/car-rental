package pl.borowa5b.car.rental.events.infrastructure

import com.fasterxml.jackson.core.JacksonException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.events.domain.model.ExternalEvent
import pl.borowa5b.car.rental.events.domain.repository.ExternalEventRepository
import pl.borowa5b.car.rental.events.domain.shared.vo.SupportedEvent
import java.util.logging.Logger

@Component
class ExternalEventProcessor(
    private val objectMapper: ObjectMapper,
    private val externalEventRepository: ExternalEventRepository,
    private val eventPublisher: ApplicationEventPublisher
) {

    private val logger = Logger.getLogger(ExternalEventProcessor::class.simpleName)

    fun process(externalEvent: ExternalEvent) {
        externalEvent.markAsProcessing()
        externalEventRepository.save(externalEvent)

        try {
            val supportedEvent = getSupportedEvent(externalEvent) ?: return
            if (externalEventRepository.exists(externalEvent.id)) {
                logger.warning("Skipping event with id ${externalEvent.id.value} because it is already processed")
                return
            }

            val event = objectMapper.readValue(externalEvent.payload, supportedEvent.payloadClass)
            eventPublisher.publishEvent(event)
            externalEvent.markAsProcessed()
            externalEventRepository.save(externalEvent)
        } catch (exception: JacksonException) {
            externalEvent.markAsFailed(exception.message)
            externalEventRepository.save(externalEvent)
        }
    }

    private fun getSupportedEvent(it: ExternalEvent): SupportedEvent? = SupportedEvents.list()
        .firstOrNull { supportedEvent -> supportedEvent.type == it.type && supportedEvent.version == it.version }
}