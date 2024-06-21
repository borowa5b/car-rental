package pl.borowa5b.car.rental.events.domain.vo

import kotlin.random.Random

object ValueObjects {

    fun externalEventId(): ExternalEventId =
        ExternalEventId("${ExternalEventId.PREFIX}${Random.nextInt(1000000, 9999999)}")

    fun applicationEventId(): ApplicationEventId =
        ApplicationEventId("${ApplicationEventId.PREFIX}${Random.nextInt(1000000, 9999999)}")
}