package pl.borowa5b.car.rental.cars.domain.model

import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId

data class Car(
    val id: CarId,
    val brand: String,
    val model: String,
    val generation: String,
    val year: Int,
    val color: String,
    val pricePerDay: Int,
    val quantity: Int,
    val version: Long = 0
)