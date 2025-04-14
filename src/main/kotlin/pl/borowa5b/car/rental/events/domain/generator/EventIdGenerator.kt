package pl.borowa5b.car.rental.events.domain.generator

import pl.borowa5b.car.rental.events.domain.vo.EventId

interface EventIdGenerator {

    fun generate(): EventId
}
