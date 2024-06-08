package pl.borowa5b.car.rental.infrastructure.scheduler

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.domain.EventPuller

@Component
@ConditionalOnProperty(prefix = "car-rental", name = ["scheduler-event-puller-enabled"], havingValue = "true")
class EventPullerScheduler(private val eventPuller: EventPuller) {

    @Scheduled(cron = "\${car-rental.scheduler-event-puller-cron}")
    fun pullEvents() {
        eventPuller.pull()
    }
}