package pl.borowa5b.car.rental.events.domain.model

import pl.borowa5b.car.rental.events.domain.shared.model.ApplicationEvent
import pl.borowa5b.car.rental.events.domain.shared.vo.ApplicationEventId
import pl.borowa5b.car.rental.events.domain.shared.vo.ApplicationEventStatus
import pl.borowa5b.car.rental.events.domain.shared.vo.ExternalEventId
import pl.borowa5b.car.rental.events.domain.shared.vo.ExternalEventStatus
import java.time.OffsetDateTime
import kotlin.random.Random

object DomainObjects {

    fun externalEventId(): ExternalEventId =
        ExternalEventId("${ExternalEventId.PREFIX}${Random.nextInt(1000000, 9999999)}")

    fun applicationEventId(): ApplicationEventId =
        ApplicationEventId("${ApplicationEventId.PREFIX}${Random.nextInt(1000000, 9999999)}")

    fun externalEvent(
        id: String = externalEventId().value,
        type: String = "CustomerCreated",
        version: String = "1.0",
        status: ExternalEventStatus = ExternalEventStatus.NEW,
        payload: String = "{}",
        errorMessage: String? = null,
        creationDate: OffsetDateTime = OffsetDateTime.parse("2022-01-01T12:00:00Z"),
        processedOnDate: OffsetDateTime? = null
    ): ExternalEvent = ExternalEvent(
        id = ExternalEventId(id),
        type = type,
        version = version,
        status = status,
        payload = payload,
        errorMessage = errorMessage,
        creationDate = creationDate,
        processedOnDate = processedOnDate,
        entityVersion = 0
    )

    fun applicationEvent(
        id: String = applicationEventId().value,
        type: String = "RentalMade",
        version: String = "1.0",
        status: ApplicationEventStatus = ApplicationEventStatus.NEW,
        payload: String = "{}",
        publishedOnDate: OffsetDateTime? = null
    ): ApplicationEvent = ApplicationEvent(
        id = ApplicationEventId(id),
        type = type,
        version = version,
        status = status,
        payload = payload,
        publishedOnDate = publishedOnDate,
        entityVersion = 0
    )
}