package pl.borowa5b.car.rental.rentals.domain.model

import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.cars.domain.shared.vo.ValueObjects.carId
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.customers.domain.shared.vo.ValueObjects.customerId
import pl.borowa5b.car.rental.rentals.domain.vo.RentalId
import pl.borowa5b.car.rental.rentals.domain.vo.RentalStatus
import pl.borowa5b.car.rental.rentals.domain.vo.ValueObjects.rentalId
import java.math.BigDecimal
import java.time.OffsetDateTime

object DomainObjects {

    fun rental(
        id: RentalId = rentalId(),
        carId: CarId = carId(),
        customerId: CustomerId = customerId(),
        status: RentalStatus = RentalStatus.STARTED,
        price: BigDecimal = BigDecimal.TEN,
        startDate: OffsetDateTime = OffsetDateTime.parse("2022-01-01T12:00:00Z"),
        endDate: OffsetDateTime = OffsetDateTime.parse("2022-01-02T12:00:00Z")
    ): Rental = Rental(
        id = id,
        carId = carId,
        customerId = customerId,
        status = status,
        price = price,
        startDate = startDate,
        endDate = endDate,
        version = 0
    )
}