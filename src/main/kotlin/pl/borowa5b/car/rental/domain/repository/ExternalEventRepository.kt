package pl.borowa5b.car.rental.domain.repository

import pl.borowa5b.car.rental.domain.model.ExternalEvent
import pl.borowa5b.car.rental.domain.model.vo.ExternalEventId
import pl.borowa5b.car.rental.infrastructure.entity.ExternalEventEntity

interface ExternalEventRepository {

    fun save(externalEvent: ExternalEvent): ExternalEventEntity

    fun exists(id: ExternalEventId): Boolean
}