package pl.borowa5b.car.rental.events.domain.shared

import pl.borowa5b.car.rental.events.domain.DomainEvent

interface ApplicationEventPublisher {

    fun publish(event: DomainEvent)
}