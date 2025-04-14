package pl.borowa5b.car.rental.events.infrastructure.scheduler

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.events.domain.repository.ApplicationEventRepository
import pl.borowa5b.car.rental.events.infrastructure.EventQueuePublisher

@Component
@ConditionalOnProperty(prefix = "car-rental", name = ["scheduler-event-publisher-enabled"], havingValue = "true")
class EventPublisherScheduler(
    private val applicationEventRepository: ApplicationEventRepository,
    private val eventPublisher: EventQueuePublisher
) {

    @Scheduled(cron = "\${car-rental.scheduler-event-publisher-cron}", zone = "UTC")
    fun publishEvents() {
        applicationEventRepository.findToPublish().forEach {
            eventPublisher.publish(it)
        }
    }
}