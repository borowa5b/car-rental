package pl.borowa5b.car.rental.rentals.infrastructure.scheduler

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.rentals.domain.RentalStarter

@Component
@ConditionalOnProperty(prefix = "car-rental", name = ["scheduler-rental-starter-enabled"], havingValue = "true")
class RentalStarterScheduler(private val rentalStarter: RentalStarter) {

    @Scheduled(cron = "\${car-rental.scheduler-rental-starter-cron}", zone = "UTC")
    fun startRentals() {
        rentalStarter.start()
    }
}