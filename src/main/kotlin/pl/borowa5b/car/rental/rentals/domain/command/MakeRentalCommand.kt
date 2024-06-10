package pl.borowa5b.car.rental.rentals.domain.command

import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import java.time.OffsetDateTime

data class MakeRentalCommand(
    val carId: CarId,
    val customerId: CustomerId,
    val startDate: OffsetDateTime,
    val endDate: OffsetDateTime
)
