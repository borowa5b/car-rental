package pl.borowa5b.car.rental.cars.application

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.cars.domain.shared.CarQuantityUpdater
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.cars.infrastructure.event.pull.RentalMadeEvent

@Component
class RentalsEventsHandler(private val carQuantityUpdater: CarQuantityUpdater) {

    @EventListener
    fun handle(event: RentalMadeEvent) {
        carQuantityUpdater.decrease(CarId(event.carId))
    }
}