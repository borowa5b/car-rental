package pl.borowa5b.car.rental.events.infrastructure.entity

import jakarta.persistence.*
import pl.borowa5b.car.rental.events.domain.model.ApplicationEvent
import pl.borowa5b.car.rental.events.domain.vo.ApplicationEventId
import pl.borowa5b.car.rental.events.domain.vo.ApplicationEventStatus
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Entity(name = "application_event")
data class ApplicationEventEntity(
    @Id
    val id: String,
    val type: String,
    val version: String,
    @Enumerated(EnumType.STRING)
    val status: ApplicationEventStatus,
    @Column(length = 1024)
    val payload: String?,
    val publishedOnDate: OffsetDateTime? = null,

    @Version
    val entityVersion: Long
) {

    val creationDate: OffsetDateTime = OffsetDateTime.now(ZoneOffset.UTC)

    fun toDomain(): ApplicationEvent = ApplicationEvent(
        ApplicationEventId(id),
        type,
        version,
        status,
        payload,
        publishedOnDate,
        entityVersion
    )

    companion object {

        fun fromDomain(applicationEvent: ApplicationEvent): ApplicationEventEntity = applicationEvent.let {
            ApplicationEventEntity(
                it.id.value,
                it.type,
                it.version,
                it.status,
                it.payload,
                it.publishedOnDate,
                it.entityVersion
            )
        }
    }
}