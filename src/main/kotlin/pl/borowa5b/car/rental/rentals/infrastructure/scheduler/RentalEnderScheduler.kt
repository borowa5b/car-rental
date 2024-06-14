package pl.borowa5b.car.rental.rentals.infrastructure.scheduler

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.rentals.domain.RentalEnder


@Component
@ConditionalOnProperty(prefix = "car-rental", name = ["scheduler-rental-ender-enabled"], havingValue = "true")
class RentalEnderScheduler(private val rentalEnder: RentalEnder) {

    @Scheduled(cron = "\${car-rental.scheduler-rental-ender-cron}", zone = "UTC")
    fun endRentals() {
        rentalEnder.end()
    }
}