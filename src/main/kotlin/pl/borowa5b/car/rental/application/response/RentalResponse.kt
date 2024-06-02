package pl.borowa5b.car.rental.application.response

import pl.borowa5b.car.rental.domain.repository.read.RentalDetails
import java.math.BigDecimal
import java.time.OffsetDateTime

data class RentalResponse(
    val id: String,
    val carId: String,
    val customerId: String,
    val status: String,
    val price: BigDecimal,
    val startDate: OffsetDateTime,
    val endDate: OffsetDateTime,
    val creationDate: OffsetDateTime
) {

    companion object {
        fun fromDetails(rentalDetails: RentalDetails): RentalResponse = RentalResponse(
            id = rentalDetails.id,
            carId = rentalDetails.carId,
            customerId = rentalDetails.customerId,
            status = rentalDetails.status,
            price = rentalDetails.price,
            startDate = rentalDetails.startDate,
            endDate = rentalDetails.endDate,
            creationDate = rentalDetails.creationDate
        )
    }
}