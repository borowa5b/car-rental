package pl.borowa5b.car.rental.events.infrastructure

import pl.borowa5b.car.rental.events.domain.model.ApplicationEvent

interface ExternalQueuePublisher {

    fun publish(event: ApplicationEvent): Result<Unit>
}