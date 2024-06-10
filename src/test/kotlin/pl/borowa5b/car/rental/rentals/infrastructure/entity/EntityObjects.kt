package pl.borowa5b.car.rental.rentals.infrastructure.entity

import pl.borowa5b.car.rental.cars.domain.shared.vo.ValueObjects.carId
import pl.borowa5b.car.rental.customers.domain.shared.vo.ValueObjects.customerId
import pl.borowa5b.car.rental.rentals.domain.model.DomainObjects.rentalId
import pl.borowa5b.car.rental.rentals.domain.vo.RentalStatus
import java.math.BigDecimal
import java.time.OffsetDateTime

object EntityObjects {

    fun rentalEntity(
        id: String = rentalId().value,
        carId: String = carId().value,
        customerId: String = customerId().value,
        status: RentalStatus = RentalStatus.IN_PROGRESS,
        price: BigDecimal = BigDecimal.TEN,
        startDate: OffsetDateTime = OffsetDateTime.parse("2022-01-01T12:00:00Z"),
        endDate: OffsetDateTime = OffsetDateTime.parse("2022-01-02T12:00:00Z")
    ): RentalEntity = RentalEntity(
        id = id,
        carId = carId,
        customerId = customerId,
        status = status,
        price = price,
        startDate = startDate,
        endDate = endDate,
        entityVersion = 0
    )
}