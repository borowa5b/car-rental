package pl.borowa5b.car.rental.events.infrastructure

import pl.borowa5b.car.rental.cars.infrastructure.event.pull.RentalMadeEvent
import pl.borowa5b.car.rental.customers.infrastructure.event.pull.CustomerCreatedEvent
import pl.borowa5b.car.rental.events.domain.vo.SupportedEvent

object SupportedEvents {

    fun list(): List<SupportedEvent> = listOf(
        SupportedEvent("CustomerCreated", "1.0", CustomerCreatedEvent::class.java),
        SupportedEvent("RentalMade", "1.0", RentalMadeEvent::class.java)
    )
}