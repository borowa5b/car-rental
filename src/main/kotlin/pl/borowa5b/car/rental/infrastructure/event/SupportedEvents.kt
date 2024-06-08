package pl.borowa5b.car.rental.infrastructure.event

import pl.borowa5b.car.rental.domain.model.vo.SupportedEvent
import pl.borowa5b.car.rental.infrastructure.event.pull.CustomerCreatedEvent

object SupportedEvents {

    fun list(): List<SupportedEvent> = listOf(
        SupportedEvent("CustomerCreated", "1.0", CustomerCreatedEvent::class.java)
    )
}