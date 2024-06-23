package pl.borowa5b.car.rental.rentals.domain

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import pl.borowa5b.car.rental.rentals.domain.shared.repository.RentalRepository
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Component
class RentalStarter(private val rentalRepository: RentalRepository) {

    @Transactional
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