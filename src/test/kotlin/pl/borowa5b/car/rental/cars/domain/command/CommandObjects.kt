package pl.borowa5b.car.rental.cars.domain.command

import pl.borowa5b.car.rental.cars.domain.vo.Brand
import java.math.BigDecimal

object CommandObjects {

    fun addCarCommand(
        brand: Brand = Brand.AUDI,
        model: String = "A3",
        generation: String = "5",
        year: Int = 2022,
        color: String = "black",
        pricePerDay: BigDecimal = BigDecimal(100),
        quantity: Int = 10,
    ): AddCarCommand = AddCarCommand(
        brand = brand,
        model = model,
        generation = generation,
        year = year,
        color = color,
        pricePerDay = pricePerDay,
        quantity = quantity
    )
}