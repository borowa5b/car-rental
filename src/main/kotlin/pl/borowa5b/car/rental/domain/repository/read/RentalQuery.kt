package pl.borowa5b.car.rental.domain.repository.read

import pl.borowa5b.car.rental.domain.model.vo.CarId
import pl.borowa5b.car.rental.domain.model.vo.CustomerId
import java.time.OffsetDateTime

data class RentalQuery(
    val carId: CarId?,
    val customerId: CustomerId?,
    val startDateFrom: OffsetDateTime?,
    val startDateTo: OffsetDateTime?,
    val endDateFrom: OffsetDateTime?,
    val endDateTo: OffsetDateTime?
)
