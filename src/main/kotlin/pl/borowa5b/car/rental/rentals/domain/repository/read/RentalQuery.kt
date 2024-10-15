package pl.borowa5b.car.rental.rentals.domain.repository.read

import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.rentals.domain.vo.RentalId
import pl.borowa5b.car.rental.rentals.domain.vo.RentalStatus
import java.time.OffsetDateTime

data class RentalQuery(
    val id: RentalId?,
    val carId: CarId?,
    val customerId: CustomerId?,
    val status: RentalStatus?,
    val startDateFrom: OffsetDateTime?,
    val startDateTo: OffsetDateTime?,
    val endDateFrom: OffsetDateTime?,
    val endDateTo: OffsetDateTime?
)
