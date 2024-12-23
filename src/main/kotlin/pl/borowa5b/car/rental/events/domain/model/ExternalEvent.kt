package pl.borowa5b.car.rental.events.domain.model

import com.fasterxml.jackson.annotation.JsonCreator
import pl.borowa5b.car.rental.events.domain.vo.ExternalEventId
import pl.borowa5b.car.rental.events.domain.vo.ExternalEventStatus
import java.time.OffsetDateTime
import java.time.ZoneOffset

data class ExternalEvent(
    val id: ExternalEventId,
    val type: String,
    val version: String,
    var status: ExternalEventStatus,
    var payload: String,
    var errorMessage: String?,
    var processedOnDate: OffsetDateTime?,
    val entityVersion: Long,
) {

    @JsonCreator
    constructor(id: String, type: String, version: String, payload: String, creationDate: OffsetDateTime) : this(
        ExternalEventId(id),
        type,
        version,
        ExternalEventStatus.NEW,
        payload,
        null,
        OffsetDateTime.now(ZoneOffset.UTC),
        0
    )

    fun markAsProcessing() {
        if (status !== ExternalEventStatus.NEW) {
            throw IllegalStateException("Cannot mark event as processing, because it is not in NEW state")
        }
        this.status = ExternalEventStatus.PROCESSING
    }

    fun markAsProcessed() {
        if (status !== ExternalEventStatus.PROCESSING) {
            throw IllegalStateException("Cannot mark event as processed, because it is not in PROCESSING state")
        }
        this.status = ExternalEventStatus.PROCESSED
        this.processedOnDate = OffsetDateTime.now(ZoneOffset.UTC)
    }

    fun markAsFailed(errorMessage: String?) {
        if (status !== ExternalEventStatus.PROCESSING) {
            throw IllegalStateException("Cannot mark event as failed, because it is not in PROCESSING state")
        }
        this.payload = "{}"
        this.errorMessage = errorMessage
        this.status = ExternalEventStatus.FAILED
    }
}