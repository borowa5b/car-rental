package pl.borowa5b.car.rental.rentals.domain.model

import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.rentals.domain.vo.RentalId
import pl.borowa5b.car.rental.rentals.domain.vo.RentalStatus
import java.math.BigDecimal
import java.time.OffsetDateTime

data class Rental(
    val id: RentalId,
    val carId: CarId,
    val customerId: CustomerId,
    var status: RentalStatus,
    val price: BigDecimal,
    val startDate: OffsetDateTime,
    val endDate: OffsetDateTime,
    val version: Long = 0
) {

    fun start(currentDate: OffsetDateTime) {
        if (status !== RentalStatus.NEW) {
            throw IllegalStateException("Rental cannot be started because it is not in status NEW")
        }
        if (currentDate.isBefore(this.startDate)) {
            throw IllegalStateException("Rental cannot be started when current date is before start date")
        }
        status = RentalStatus.STARTED
    }

    fun end(currentDate: OffsetDateTime) {
        if (status !== RentalStatus.STARTED) {
            throw IllegalStateException("Rental cannot be ended because it is not in status STARTED")
        }
        if (currentDate.isBefore(endDate)) {
            throw IllegalStateException("Rental cannot be ended when current date is before end date")
        }
        status = RentalStatus.ENDED
    }
}
