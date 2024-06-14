package pl.borowa5b.car.rental.rentals.domain

import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.rentals.domain.repository.RentalRepository
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Component
class RentalEnder(private val rentalRepository: RentalRepository) {

    fun end() {
        val currentDate = OffsetDateTime.now(ZoneOffset.UTC)
        var rentalId = rentalRepository.findToEnd(currentDate)
        while (rentalId != null) {
            val rental = rentalRepository.findById(rentalId)!!
            rental.end(currentDate)
            rentalRepository.save(rental)

            rentalId = rentalRepository.findToEnd(currentDate)
        }
    }
}