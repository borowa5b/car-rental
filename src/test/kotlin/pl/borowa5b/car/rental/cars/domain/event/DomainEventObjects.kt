package pl.borowa5b.car.rental.cars.domain.event

import pl.borowa5b.car.rental.cars.domain.shared.vo.ValueObjects.carId
import pl.borowa5b.car.rental.cars.domain.vo.Brand
import java.math.BigDecimal

object DomainEventObjects {

    fun carAddedEvent(
        id: String = carId().value,
        brand: String = Brand.TOYOTA.name,
        model: String = "corolla",
        generation: String = "3",
        year: Int = 2020,
        color: String = "black",
        pricePerDay: BigDecimal = BigDecimal(100),
        quantity: Int = 10
    ): CarAddedEvent = CarAddedEvent(
        id = id,
        brand = brand,
        model = model,
        generation = generation,
        year = year,
        color = color,
        pricePerDay = pricePerDay,
        quantity = quantity,
    )

    fun carEditedEvent(
        id: String = carId().value,
        brand: String = Brand.TOYOTA.name,
        model: String = "corolla",
        generation: String = "3",
        year: Int = 2020,
        color: String = "black",
        pricePerDay: BigDecimal = BigDecimal(100),
        quantity: Int = 10
    ): CarEditedEvent = CarEditedEvent(
        id = id,
        brand = brand,
        model = model,
        generation = generation,
        year = year,
        color = color,
        pricePerDay = pricePerDay,
        quantity = quantity,
    )
}