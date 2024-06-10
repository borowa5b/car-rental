package pl.borowa5b.car.rental.customers.domain.shared.vo

import kotlin.random.Random

object ValueObjects {

    fun customerId(): CustomerId = CustomerId("${CustomerId.PREFIX}${Random.nextInt(1000000, 9999999)}")
}