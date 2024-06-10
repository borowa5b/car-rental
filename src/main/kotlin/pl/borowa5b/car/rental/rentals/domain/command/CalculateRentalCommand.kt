package pl.borowa5b.car.rental.rentals.domain.command

import java.time.OffsetDateTime

data class CalculateRentalCommand(
    val startDate: OffsetDateTime,
    val endDate: OffsetDateTime
)
