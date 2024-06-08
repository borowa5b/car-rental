package pl.borowa5b.car.rental.domain.repository

import pl.borowa5b.car.rental.domain.model.ApplicationEvent
import pl.borowa5b.car.rental.infrastructure.entity.ApplicationEventEntity

interface ApplicationEventRepository {

    fun save(applicationEvent: ApplicationEvent): ApplicationEventEntity
}