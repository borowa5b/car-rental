package pl.borowa5b.car.rental.domain.repository.read

import java.math.BigDecimal
import java.time.OffsetDateTime

data class RentalDetails(
    val id: String,
    val carId: String,
    val customerId: String,
    val status: String,
    val price: BigDecimal,
    val startDate: OffsetDateTime,
    val endDate: OffsetDateTime,
    val creationDate: OffsetDateTime,
    val modificationDate: OffsetDateTime,
    val entityVersion: Int
)