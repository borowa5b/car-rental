package pl.borowa5b.car.rental.rentals.domain

import java.math.BigDecimal

interface PriceConfiguration {

    val pricePerDay: BigDecimal
}