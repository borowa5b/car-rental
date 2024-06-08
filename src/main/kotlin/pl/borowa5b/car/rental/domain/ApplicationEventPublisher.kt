package pl.borowa5b.car.rental.domain

import pl.borowa5b.car.rental.domain.model.DomainEvent

interface ApplicationEventPublisher {

    fun publish(event: DomainEvent)
}