package pl.borowa5b.car.rental.domain.generator

import pl.borowa5b.car.rental.domain.model.vo.ApplicationEventId

interface ApplicationEventIdGenerator {

    fun generate(): ApplicationEventId
}
