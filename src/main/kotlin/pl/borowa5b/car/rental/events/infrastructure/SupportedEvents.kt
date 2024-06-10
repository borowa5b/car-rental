package pl.borowa5b.car.rental.events.infrastructure

import pl.borowa5b.car.rental.events.domain.shared.vo.SupportedEvent
import pl.borowa5b.car.rental.customers.infrastructure.event.pull.CustomerCreatedEvent

object SupportedEvents {

    fun list(): List<SupportedEvent> = listOf(
        SupportedEvent("CustomerCreated", "1.0", CustomerCreatedEvent::class.java)
    )
}