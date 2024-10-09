package pl.borowa5b.car.rental.cars.application.response

import pl.borowa5b.car.rental.cars.domain.repository.read.CarDetails
import java.math.BigDecimal
import java.time.OffsetDateTime

data class CarResponse(
    val id: String,
    val brand: String,
    val model: String,
    val generation: String,
    val productionYear: Int,
    val color: String,
    val pricePerDay: BigDecimal,
    val quantity: Int,
    val creationDate: OffsetDateTime
) {

    companion object {

        fun fromDetails(carDetails: CarDetails): CarResponse = CarResponse(
            id = carDetails.id,
            brand = carDetails.brand,
            model = carDetails.model,
            generation = carDetails.generation,
            productionYear = carDetails.productionYear,
            color = carDetails.color,
            pricePerDay = carDetails.pricePerDay,
            quantity = carDetails.quantity,
            creationDate = carDetails.creationDate
        )
    }
}