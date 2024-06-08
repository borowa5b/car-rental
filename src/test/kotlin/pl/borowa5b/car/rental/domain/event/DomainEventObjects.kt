package pl.borowa5b.car.rental.domain.event

import pl.borowa5b.car.rental.domain.model.DomainObjects.carId
import pl.borowa5b.car.rental.domain.model.DomainObjects.customerId
import pl.borowa5b.car.rental.domain.model.DomainObjects.rentalId
import pl.borowa5b.car.rental.domain.model.vo.CarId
import pl.borowa5b.car.rental.domain.model.vo.CustomerId
import pl.borowa5b.car.rental.domain.model.vo.RentalId
import java.math.BigDecimal
import java.time.OffsetDateTime

object DomainEventObjects {

    fun rentalMadeEvent(
        rentalId: RentalId = rentalId(),
        carId: CarId = carId(),
        customerId: CustomerId = customerId(),
        startDate: OffsetDateTime = OffsetDateTime.parse("2022-01-01T12:00:00Z"),
        endDate: OffsetDateTime = OffsetDateTime.parse("2022-01-02T12:00:00Z"),
        price: BigDecimal = BigDecimal.TEN
    ): RentalMadeEvent = RentalMadeEvent(
        rentalId = rentalId.value,
        carId = carId.value,
        customerId = customerId.value,
        startDate = startDate,
        endDate = endDate,
        price = price
    )
}