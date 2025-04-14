package pl.borowa5b.car.rental.events.infrastructure

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import pl.borowa5b.car.rental.events.domain.model.ApplicationEvent
import pl.borowa5b.car.rental.events.domain.repository.ApplicationEventRepository
import pl.borowa5b.car.rental.events.domain.vo.ApplicationEventStatus
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.logging.Level
import java.util.logging.Logger

@Component
class EventQueuePublisher(
    private val repository: ApplicationEventRepository,
    private val externalQueuePublisher: ExternalQueuePublisher
) {

    companion object {
        private val LOGGER = Logger.getLogger(EventQueuePublisher::class.simpleName)
    }

    @Transactional
    fun publish(event: ApplicationEvent) {
        startPublishing(event)
        val publishingResult = externalQueuePublisher.publish(event)
        if (publishingResult.isSuccess) {
            event.publishedOnDate = OffsetDateTime.now(ZoneOffset.UTC)
            event.status = ApplicationEventStatus.PUBLISHED
        } else {
            LOGGER.log(Level.WARNING, "Failed to publish event with id ${event.id}", publishingResult.exceptionOrNull())
            event.status = ApplicationEventStatus.FAILED
        }
        repository.save(event)
    }

    private fun startPublishing(event: ApplicationEvent) {
        event.status = ApplicationEventStatus.PUBLISHING
        repository.save(event)
    }
}