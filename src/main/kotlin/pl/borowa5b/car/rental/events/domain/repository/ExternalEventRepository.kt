package pl.borowa5b.car.rental.events.domain.repository

import pl.borowa5b.car.rental.events.domain.model.ExternalEvent
import pl.borowa5b.car.rental.events.domain.vo.EventId
import pl.borowa5b.car.rental.events.domain.vo.ExternalEventStatus

interface ExternalEventRepository {

    fun save(externalEvent: ExternalEvent): ExternalEvent

    fun existsInStatus(id: EventId, status: ExternalEventStatus): Boolean
}