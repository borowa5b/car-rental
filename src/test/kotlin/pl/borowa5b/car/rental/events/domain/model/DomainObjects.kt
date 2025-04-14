package pl.borowa5b.car.rental.events.domain.model

import pl.borowa5b.car.rental.events.domain.vo.ApplicationEventStatus
import pl.borowa5b.car.rental.events.domain.vo.EventId
import pl.borowa5b.car.rental.events.domain.vo.ExternalEventStatus
import pl.borowa5b.car.rental.events.domain.vo.ValueObjects.eventId
import java.time.OffsetDateTime

object DomainObjects {

    fun externalEvent(
        id: String = eventId().value,
        type: String = "CustomerCreated",
        version: String = "1.0",
        status: ExternalEventStatus = ExternalEventStatus.NEW,
        payload: String = "{\"id\": \"CTR543654233453453\", \"name\": \"John\", \"surname\": \"Doe\", \"email\": \"john.doe@gmail.com\", \"phone\": \"123456789\", \"address\": \"Zielona Góra, Podgórna 1z/3\", \"documentNumber\": \"ABC123456\"}",
        errorMessage: String? = null,
        processedOnDate: OffsetDateTime? = null
    ): ExternalEvent = ExternalEvent(
        id = EventId(id),
        type = type,
        version = version,
        status = status,
        payload = payload,
        errorMessage = errorMessage,
        processedOnDate = processedOnDate,
        entityVersion = 0
    )

    fun applicationEvent(
        id: String = eventId().value,
        type: String = "RentalMade",
        version: String = "1.0",
        status: ApplicationEventStatus = ApplicationEventStatus.NEW,
        payload: String = "{}",
        publishedOnDate: OffsetDateTime? = null
    ): ApplicationEvent = ApplicationEvent(
        id = EventId(id),
        type = type,
        version = version,
        status = status,
        payload = payload,
        publishedOnDate = publishedOnDate,
        entityVersion = 0
    )
}