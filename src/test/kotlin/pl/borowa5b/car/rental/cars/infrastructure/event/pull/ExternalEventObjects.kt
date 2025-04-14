package pl.borowa5b.car.rental.cars.infrastructure.event.pull

import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.cars.domain.shared.vo.ValueObjects.carId

object ExternalEventObjects {

    fun rentalMadeEvent(
        carId: CarId = carId()
    ): RentalMadeEvent = RentalMadeEvent(
        carId = carId.value
    )
}