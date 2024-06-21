package pl.borowa5b.car.rental.events.domain.repository

import pl.borowa5b.car.rental.events.domain.model.ExternalEvent
import pl.borowa5b.car.rental.events.domain.vo.ExternalEventId

interface ExternalEventRepository {

    fun save(externalEvent: ExternalEvent): ExternalEvent

    fun exists(id: ExternalEventId): Boolean
}