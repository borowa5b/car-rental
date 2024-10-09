package pl.borowa5b.car.rental.cars.domain.repository.read

import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.cars.domain.shared.vo.ValueObjects.carId
import pl.borowa5b.car.rental.cars.domain.vo.Brand
import java.math.BigDecimal
import java.time.OffsetDateTime

object ReadObjects {

    fun carDetails(
        id: CarId = carId(),
        brand: Brand = Brand.TESLA,
        model: String = "Model S",
        generation: String = "1",
        productionYear: Int = 2022,
        color: String = "Black",
        pricePerDay: BigDecimal = BigDecimal.TEN,
        quantity: Int = 10
    ): CarDetails = CarDetails(
        id = id.value,
        brand = brand.name,
        model = model,
        generation = generation,
        productionYear = productionYear,
        color = color,
        pricePerDay = pricePerDay,
        quantity = quantity,
        creationDate = OffsetDateTime.parse("2022-01-01T12:00:00Z"),
        modificationDate = OffsetDateTime.parse("2022-01-01T12:00:00Z"),
        entityVersion = 0
    )

    fun carQuery(
        id: CarId? = null,
        brand: Brand? = null,
        model: String? = null,
        generation: String? = null,
        productionYear: Int? = null,
        color: String? = null,
        pricePerDayFrom: BigDecimal? = null,
        pricePerDayTo: BigDecimal? = null,
        quantityFrom: Int? = null,
        quantityTo: Int? = null
    ): CarQuery = CarQuery(
        id = id,
        brand = brand,
        model = model,
        generation = generation,
        productionYear = productionYear,
        color = color,
        pricePerDayFrom = pricePerDayFrom,
        pricePerDayTo = pricePerDayTo,
        quantityFrom = quantityFrom,
        quantityTo = quantityTo
    )
}