package pl.borowa5b.car.rental.rentals.domain

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import pl.borowa5b.car.rental.cars.domain.shared.CarQuantityUpdater
import pl.borowa5b.car.rental.rentals.domain.shared.repository.RentalRepository
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Component
class RentalEnder(private val rentalRepository: RentalRepository, private val carQuantityUpdater: CarQuantityUpdater) {

    @Transactional
    fun end() {
        val currentDate = OffsetDateTime.now(ZoneOffset.UTC)
        var rentalId = rentalRepository.findToEnd(currentDate)
        while (rentalId != null) {
            val rental = rentalRepository.findById(rentalId)!!
            rental.end(currentDate)
            carQuantityUpdater.increase(rental.carId)
            rentalRepository.save(rental)

            rentalId = rentalRepository.findToEnd(currentDate)
        }
    }
}