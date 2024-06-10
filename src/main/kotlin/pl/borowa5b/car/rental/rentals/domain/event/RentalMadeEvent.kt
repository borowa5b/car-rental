package pl.borowa5b.car.rental.rentals.domain.event

import pl.borowa5b.car.rental.events.domain.DomainEvent
import pl.borowa5b.car.rental.rentals.domain.model.Rental
import java.math.BigDecimal
import java.time.OffsetDateTime

class RentalMadeEvent(
    val rentalId: String,
    val carId: String,
    val customerId: String,
    val startDate: OffsetDateTime,
    val endDate: OffsetDateTime,
    val price: BigDecimal,
) : DomainEvent {

    constructor(rental: Rental) : this(
        rental.id.value,
        rental.carId.value,
        rental.customerId.value,
        rental.startDate,
        rental.endDate,
        rental.price
    )

    override fun getType(): String = "RentalMade"

    override fun getVersion(): String = "1.0"
}