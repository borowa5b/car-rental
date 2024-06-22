package pl.borowa5b.car.rental.cars.domain.command

import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.cars.domain.vo.Brand
import java.math.BigDecimal

data class EditCarCommand(
    val carId: CarId,
    val brand: Brand?,
    val model: String?,
    val generation: String?,
    val year: Int?,
    val color: String?,
    val pricePerDay: BigDecimal?,
    val quantity: Int?
)
