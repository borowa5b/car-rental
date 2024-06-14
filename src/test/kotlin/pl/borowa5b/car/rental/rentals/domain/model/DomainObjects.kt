package pl.borowa5b.car.rental.rentals.domain.model

import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.cars.domain.shared.vo.ValueObjects.carId
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.customers.domain.shared.vo.ValueObjects.customerId
import pl.borowa5b.car.rental.rentals.domain.repository.read.RentalDetails
import pl.borowa5b.car.rental.rentals.domain.repository.read.RentalQuery
import pl.borowa5b.car.rental.rentals.domain.vo.RentalId
import pl.borowa5b.car.rental.rentals.domain.vo.RentalStatus
import java.math.BigDecimal
import java.time.OffsetDateTime
import kotlin.random.Random

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

    fun rentalId(): RentalId = RentalId("${RentalId.PREFIX}${Random.nextInt(1000000, 9999999)}")

    fun rentalQuery(
        carId: CarId? = null,
        customerId: CustomerId? = null,
        startDateFrom: OffsetDateTime? = null,
        startDateTo: OffsetDateTime? = null,
        endDateFrom: OffsetDateTime? = null,
        endDateTo: OffsetDateTime? = null,
    ): RentalQuery = RentalQuery(
        carId = carId,
        customerId = customerId,
        startDateFrom = startDateFrom,
        startDateTo = startDateTo,
        endDateFrom = endDateFrom,
        endDateTo = endDateTo
    )

    fun rentalDetails(
        id: RentalId = rentalId(),
        carId: CarId = carId(),
        customerId: CustomerId = customerId(),
        status: RentalStatus = RentalStatus.STARTED,
        price: BigDecimal = BigDecimal.TEN,
        startDate: OffsetDateTime = OffsetDateTime.parse("2022-01-01T12:00:00Z"),
        endDate: OffsetDateTime = OffsetDateTime.parse("2022-01-02T12:00:00Z")
    ): RentalDetails = RentalDetails(
        id = id.value,
        carId = carId.value,
        customerId = customerId.value,
        status = status.name,
        price = price,
        startDate = startDate,
        endDate = endDate,
        creationDate = OffsetDateTime.parse("2022-01-01T12:00:00Z"),
        modificationDate = OffsetDateTime.parse("2022-01-01T12:00:00Z"),
        entityVersion = 0
    )
}