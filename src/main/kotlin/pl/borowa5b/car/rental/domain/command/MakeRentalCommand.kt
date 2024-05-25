package pl.borowa5b.car.rental.domain.command

import pl.borowa5b.car.rental.domain.model.CarId
import pl.borowa5b.car.rental.domain.model.CustomerId
import java.time.OffsetDateTime

data class MakeRentalCommand(
    val carId: CarId,
    val customerId: CustomerId,
    val startDate: OffsetDateTime,
    val endDate: OffsetDateTime
)
