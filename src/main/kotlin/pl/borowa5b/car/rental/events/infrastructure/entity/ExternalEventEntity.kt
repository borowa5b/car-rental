package pl.borowa5b.car.rental.events.infrastructure.entity

import jakarta.persistence.*
import pl.borowa5b.car.rental.events.domain.model.ExternalEvent
import pl.borowa5b.car.rental.events.domain.vo.ExternalEventId
import pl.borowa5b.car.rental.events.domain.vo.ExternalEventStatus
import java.time.OffsetDateTime

@Entity(name = "external_event")
data class ExternalEventEntity(
    @Id
    val id: String,
    val type: String,
    val version: String,
    @Enumerated(EnumType.STRING)
    val status: ExternalEventStatus,
    val payload: String,
    val errorMessage: String?,
    val creationDate: OffsetDateTime,
    var processedOnDate: OffsetDateTime? = null,

    @Version
    val entityVersion: Long
) {

    fun toDomain(): ExternalEvent = ExternalEvent(
        ExternalEventId(id),
        type,
        version,
        status,
        payload,
        errorMessage,
        creationDate,
        processedOnDate,
        entityVersion
    )

    companion object {

        fun fromDomain(externalEvent: ExternalEvent): ExternalEventEntity = externalEvent.let {
            ExternalEventEntity(
                it.id.value,
                it.type,
                it.version,
                it.status,
                it.payload,
                it.errorMessage,
                it.creationDate,
                it.processedOnDate,
                it.entityVersion
            )
        }
    }
}