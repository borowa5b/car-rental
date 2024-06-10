package pl.borowa5b.car.rental.rentals.domain.command

import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import java.time.OffsetDateTime

object CommandObjects {

    fun calculateRentalCommand(
        startDate: OffsetDateTime = OffsetDateTime.parse("2022-01-01T12:00:00Z"),
        endDate: OffsetDateTime = OffsetDateTime.parse("2022-01-02T12:00:00Z")
    ): CalculateRentalCommand = CalculateRentalCommand(
        startDate = startDate,
        endDate = endDate
    )

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