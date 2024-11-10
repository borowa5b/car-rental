package pl.borowa5b.car.rental.events.infrastructure.entity

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import pl.borowa5b.car.rental.events.domain.model.ExternalEvent
import pl.borowa5b.car.rental.events.domain.vo.ExternalEventId
import pl.borowa5b.car.rental.events.domain.vo.ExternalEventStatus
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Entity(name = "external_event")
data class ExternalEventEntity(
    @Id
    val id: String,
    val type: String,
    val version: String,
    @Enumerated(EnumType.STRING)
    val status: ExternalEventStatus,
    @JdbcTypeCode(SqlTypes.JSON)
    val payload: String,
    @Column(length = 1024)
    val errorMessage: String?,
    var processedOnDate: OffsetDateTime? = null,

    @Version
    val entityVersion: Long
) {
    val creationDate: OffsetDateTime = OffsetDateTime.now(ZoneOffset.UTC)
    var modificationDate: OffsetDateTime = OffsetDateTime.now(ZoneOffset.UTC)

    @PreUpdate
    fun preUpdate() {
        modificationDate = OffsetDateTime.now(ZoneOffset.UTC)
    }

    fun toDomain(): ExternalEvent = ExternalEvent(
        ExternalEventId(id),
        type,
        version,
        status,
        payload,
        errorMessage,
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
                it.processedOnDate,
                it.entityVersion
            )
        }
    }
}