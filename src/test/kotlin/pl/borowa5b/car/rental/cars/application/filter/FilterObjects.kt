package pl.borowa5b.car.rental.cars.application.filter

import java.math.BigDecimal

object FilterObjects {

    fun getCarsFilter(
        id: String? = null,
        brand: String? = null,
        model: String? = null,
        generation: String? = null,
        productionYear: Int? = null,
        color: String? = null,
        pricePerDayFrom: BigDecimal? = null,
        pricePerDayTo: BigDecimal? = null,
        quantityFrom: Int? = null,
        quantityTo: Int? = null
    ): GetCarsFilter = GetCarsFilter(
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