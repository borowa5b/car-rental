package pl.borowa5b.car.rental.events.domain.vo

import kotlin.random.Random

object ValueObjects {

    fun eventId(): EventId = EventId("${EventId.PREFIX}${Random.nextInt(1000000, 9999999)}")
}