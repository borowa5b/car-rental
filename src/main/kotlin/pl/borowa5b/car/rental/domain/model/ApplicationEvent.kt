package pl.borowa5b.car.rental.domain.model

import pl.borowa5b.car.rental.domain.model.vo.ApplicationEventId
import pl.borowa5b.car.rental.domain.model.vo.ApplicationEventStatus
import java.time.OffsetDateTime

data class ApplicationEvent(
    val id: ApplicationEventId,
    val type: String,
    val version: String,
    val status: ApplicationEventStatus,
    val payload: String?,
    val publishedOnDate: OffsetDateTime? = null,
    val entityVersion: Long = 0
)
