package pl.borowa5b.car.rental.rentals.domain

import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.rentals.domain.repository.RentalRepository
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Component
class RentalStarter(private val rentalRepository: RentalRepository) {

    fun start() {
        val currentDate = OffsetDateTime.now(ZoneOffset.UTC)
        var rentalId = rentalRepository.findToStart(currentDate)
        while (rentalId != null) {
            val rental = rentalRepository.findById(rentalId)!!
            rental.start(currentDate)
            rentalRepository.save(rental)

            rentalId = rentalRepository.findToStart(currentDate)
        }
    }
}