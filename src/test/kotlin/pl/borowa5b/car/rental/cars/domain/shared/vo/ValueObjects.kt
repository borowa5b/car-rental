package pl.borowa5b.car.rental.cars.domain.shared.vo

import kotlin.random.Random

object ValueObjects {

    fun carId(): CarId = CarId("${CarId.PREFIX}${Random.nextInt(1000000, 9999999)}")
}