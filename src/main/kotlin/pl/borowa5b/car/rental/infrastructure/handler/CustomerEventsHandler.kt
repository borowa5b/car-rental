package pl.borowa5b.car.rental.infrastructure.handler

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.domain.CustomerSaver
import pl.borowa5b.car.rental.infrastructure.event.pull.CustomerCreatedEvent

@Component
class CustomerEventsHandler(private val customerSaver: CustomerSaver) {

    @EventListener(value = [CustomerCreatedEvent::class])
    fun handle(event: CustomerCreatedEvent) {
        customerSaver.save(event.toCommand())
    }
}