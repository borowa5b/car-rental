package pl.borowa5b.car.rental.rentals.domain.vo

import kotlin.random.Random

object ValueObjects {

    fun rentalId(): RentalId = RentalId("${RentalId.PREFIX}${Random.nextInt(1000000, 9999999)}")
}