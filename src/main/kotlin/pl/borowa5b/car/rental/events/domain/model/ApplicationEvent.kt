package pl.borowa5b.car.rental.events.domain.model

import com.fasterxml.jackson.annotation.JsonUnwrapped
import pl.borowa5b.car.rental.events.domain.vo.ApplicationEventStatus
import pl.borowa5b.car.rental.events.domain.vo.EventId
import java.time.OffsetDateTime

data class ApplicationEvent(
    @JsonUnwrapped
    val id: EventId,
    val type: String,
    val version: String,
    var status: ApplicationEventStatus,
    val payload: String?,
    var publishedOnDate: OffsetDateTime? = null,
    val entityVersion: Long = 0
)
