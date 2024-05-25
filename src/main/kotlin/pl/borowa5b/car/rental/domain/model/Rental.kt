package pl.borowa5b.car.rental.domain.model

import java.math.BigDecimal
import java.time.OffsetDateTime

data class Rental(
    val id: RentalId,
    val carId: CarId,
    val customerId: CustomerId,
    val status: RentalStatus,
    val price: BigDecimal,
    val startDate: OffsetDateTime,
    val endDate: OffsetDateTime,
    val version: Long = 0
)
