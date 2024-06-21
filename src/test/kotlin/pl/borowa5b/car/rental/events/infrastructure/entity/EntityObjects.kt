package pl.borowa5b.car.rental.events.infrastructure.entity

import pl.borowa5b.car.rental.events.domain.vo.ApplicationEventStatus
import pl.borowa5b.car.rental.events.domain.vo.ExternalEventStatus
import pl.borowa5b.car.rental.events.domain.vo.ValueObjects.applicationEventId
import pl.borowa5b.car.rental.events.domain.vo.ValueObjects.externalEventId
import java.time.OffsetDateTime

object EntityObjects {

    fun externalEventEntity(
        id: String = externalEventId().value,
        type: String = "CustomerCreated",
        version: String = "1.0",
        status: ExternalEventStatus = ExternalEventStatus.NEW,
        payload: String = "{}",
        errorMessage: String? = null,
        creationDate: OffsetDateTime = OffsetDateTime.parse("2022-01-01T12:00:00Z"),
        processedOnDate: OffsetDateTime? = null
    ): ExternalEventEntity = ExternalEventEntity(
        id = id,
        type = type,
        version = version,
        status = status,
        payload = payload,
        errorMessage = errorMessage,
        creationDate = creationDate,
        processedOnDate = processedOnDate,
        entityVersion = 0
    )

    fun applicationEventEntity(
        id: String = applicationEventId().value,
        type: String = "RentalMade",
        version: String = "1.0",
        status: ApplicationEventStatus = ApplicationEventStatus.NEW,
        payload: String = "{}",
        publishedOnDate: OffsetDateTime? = null
    ): ApplicationEventEntity = ApplicationEventEntity(
        id = id,
        type = type,
        version = version,
        status = status,
        payload = payload,
        publishedOnDate = publishedOnDate,
        entityVersion = 0
    )
}