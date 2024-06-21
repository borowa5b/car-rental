package pl.borowa5b.car.rental.cars.domain.model

import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.cars.domain.vo.Brand
import java.math.BigDecimal

data class Car(
    val id: CarId,
    val brand: Brand,
    val model: String,
    val generation: String,
    val year: Int,
    val color: String,
    val pricePerDay: BigDecimal,
    val quantity: Int,
    val version: Long = 0
)