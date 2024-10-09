package pl.borowa5b.car.rental.cars.application.request

import pl.borowa5b.car.rental.cars.domain.shared.vo.ValueObjects.carId
import pl.borowa5b.car.rental.cars.domain.vo.Brand
import java.math.BigDecimal

object RequestObjects {

    fun addCarRequest(
        brand: String? = Brand.TOYOTA.name,
        model: String? = "Corolla",
        generation: String? = "3",
        productionYear: Int? = 2004,
        color: String? = "Silver",
        pricePerDay: BigDecimal? = BigDecimal(50),
        quantity: Int? = 10
    ): AddCarRequest = AddCarRequest(
        brand = brand,
        model = model,
        generation = generation,
        productionYear = productionYear,
        color = color,
        pricePerDay = pricePerDay,
        quantity = quantity
    )

    fun editCarRequest(
        carId: String = carId().value,
        brand: String? = null,
        model: String? = null,
        generation: String? = null,
        productionYear: Int? = null,
        color: String? = null,
        pricePerDay: BigDecimal? = null,
        quantity: Int? = null
    ): EditCarRequest = EditCarRequest(
        carId = carId,
        brand = brand,
        model = model,
        generation = generation,
        productionYear = productionYear,
        color = color,
        pricePerDay = pricePerDay,
        quantity = quantity
    )
}