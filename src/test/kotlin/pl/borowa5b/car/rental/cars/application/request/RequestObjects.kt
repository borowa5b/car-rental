package pl.borowa5b.car.rental.cars.application.request

import pl.borowa5b.car.rental.cars.domain.vo.Brand
import java.math.BigDecimal

object RequestObjects {

    fun addCarRequest(
        brand: String? = Brand.TOYOTA.name,
        model: String? = "Corolla",
        generation: String? = "3",
        year: Int? = 2004,
        color: String? = "Silver",
        pricePerDay: BigDecimal? = BigDecimal(50),
        quantity: Int? = 10
    ): AddCarRequest = AddCarRequest(
        brand = brand,
        model = model,
        generation = generation,
        year = year,
        color = color,
        pricePerDay = pricePerDay,
        quantity = quantity
    )
}