package pl.borowa5b.car.rental.cars.domain.command

import pl.borowa5b.car.rental.cars.domain.vo.Brand
import java.math.BigDecimal

data class AddCarCommand(
    val brand: Brand,
    val model: String,
    val generation: String,
    val year: Int,
    val color: String,
    val pricePerDay: BigDecimal,
    val quantity: Int
)
