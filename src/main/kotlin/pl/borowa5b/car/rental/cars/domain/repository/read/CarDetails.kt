package pl.borowa5b.car.rental.cars.domain.repository.read

import java.math.BigDecimal
import java.time.OffsetDateTime

data class CarDetails(
    val id: String,
    val brand: String,
    val model: String,
    val generation: String,
    val productionYear: Int,
    val color: String,
    var pricePerDay: BigDecimal,
    val quantity: Int,
    val creationDate: OffsetDateTime,
    val modificationDate: OffsetDateTime,
    val entityVersion: Int
)
