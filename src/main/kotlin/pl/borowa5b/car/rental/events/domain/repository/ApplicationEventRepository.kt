package pl.borowa5b.car.rental.events.domain.repository

import pl.borowa5b.car.rental.events.domain.model.ApplicationEvent

interface ApplicationEventRepository {

    fun save(applicationEvent: ApplicationEvent): ApplicationEvent

    fun findAll(): List<ApplicationEvent>
}