package pl.borowa5b.car.rental.events.infrastructure

import com.fasterxml.jackson.core.JacksonException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import pl.borowa5b.car.rental.events.domain.model.ExternalEvent
import pl.borowa5b.car.rental.events.domain.repository.ExternalEventRepository
import pl.borowa5b.car.rental.events.domain.vo.ExternalEventStatus
import pl.borowa5b.car.rental.events.domain.vo.SupportedEvent
import java.util.logging.Logger

@Component
class ExternalEventProcessor(
    private val objectMapper: ObjectMapper,
    private val externalEventRepository: ExternalEventRepository,
    private val eventPublisher: ApplicationEventPublisher
) {

    companion object {

        private val LOGGER = Logger.getLogger(ExternalEventProcessor::class.simpleName)
    }

    @Transactional
    fun process(externalEvent: ExternalEvent) {
        val supportedEvent = getSupportedEvent(externalEvent) ?: return
        if (externalEventRepository.existsInStatus(externalEvent.id, ExternalEventStatus.PROCESSED)) {
            LOGGER.warning("Skipping event with id ${externalEvent.id.value} because it is already processed")
            return
        }
        externalEvent.markAsProcessing()

        try {
            val event = objectMapper.readValue(externalEvent.payload, supportedEvent.payloadClass)
            externalEvent.markAsProcessed()
            externalEventRepository.save(externalEvent)
            eventPublisher.publishEvent(event)
        } catch (exception: JacksonException) {
            externalEvent.markAsFailed(exception.message)
            externalEventRepository.save(externalEvent)
        }
    }

    private fun getSupportedEvent(externalEvent: ExternalEvent): SupportedEvent? = SupportedEvents.list()
        .firstOrNull { supportedEvent -> supportedEvent.type == externalEvent.type && supportedEvent.version == externalEvent.version }
}