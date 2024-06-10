package pl.borowa5b.car.rental.events.domain.generator

import pl.borowa5b.car.rental.events.domain.shared.vo.ApplicationEventId

interface ApplicationEventIdGenerator {

    fun generate(): ApplicationEventId
}
