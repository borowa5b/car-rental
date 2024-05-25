package pl.borowa5b.car.rental.domain

import java.math.BigDecimal

interface PriceConfiguration {

    val pricePerDay: BigDecimal
}