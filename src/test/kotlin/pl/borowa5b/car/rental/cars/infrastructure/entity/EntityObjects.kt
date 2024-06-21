package pl.borowa5b.car.rental.cars.infrastructure.entity

import pl.borowa5b.car.rental.cars.domain.shared.vo.ValueObjects.carId
import pl.borowa5b.car.rental.cars.domain.vo.Brand
import java.math.BigDecimal

object EntityObjects {

    fun carEntity(
        id: String = carId().value,
        brand: Brand = Brand.TOYOTA,
        model: String = "Corolla",
        generation: String = "LE",
        year: Int = 2022,
        color: String = "Blue",
        pricePerDay: BigDecimal = BigDecimal("200"),
        quantity: Int = 5
    ): CarEntity = CarEntity(
        id = id,
        brand = brand,
        model = model,
        generation = generation,
        year = year,
        color = color,
        pricePerDay = pricePerDay,
        quantity = quantity,
        entityVersion = 0
    )
}