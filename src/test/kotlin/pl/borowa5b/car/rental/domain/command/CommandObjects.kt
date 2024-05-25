package pl.borowa5b.car.rental.domain.command

import pl.borowa5b.car.rental.domain.model.CarId
import pl.borowa5b.car.rental.domain.model.CustomerId
import java.time.OffsetDateTime

object CommandObjects {

    fun makeRentalCommand(
        carId: CarId,
        customerId: CustomerId,
        startDate: OffsetDateTime = OffsetDateTime.parse("2022-01-01T12:00:00Z"),
        endDate: OffsetDateTime = OffsetDateTime.parse("2022-01-02T12:00:00Z")
    ): MakeRentalCommand = MakeRentalCommand(
        carId = carId,
        customerId = customerId,
        startDate = startDate,
        endDate = endDate
    )
}