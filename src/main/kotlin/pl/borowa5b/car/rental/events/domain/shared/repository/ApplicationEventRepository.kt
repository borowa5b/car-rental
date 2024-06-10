package pl.borowa5b.car.rental.events.domain.shared.repository

import pl.borowa5b.car.rental.events.domain.shared.model.ApplicationEvent

interface ApplicationEventRepository {

    fun save(applicationEvent: ApplicationEvent): ApplicationEvent

    fun findAll(): List<ApplicationEvent>
}