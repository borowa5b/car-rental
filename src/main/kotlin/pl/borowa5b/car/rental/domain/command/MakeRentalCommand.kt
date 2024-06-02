package pl.borowa5b.car.rental.domain.command

import pl.borowa5b.car.rental.domain.model.vo.CarId
import pl.borowa5b.car.rental.domain.model.vo.CustomerId
import java.time.OffsetDateTime

data class MakeRentalCommand(
    val carId: CarId,
    val customerId: CustomerId,
    val startDate: OffsetDateTime,
    val endDate: OffsetDateTime
)
