package pl.borowa5b.car.rental.rentals.domain

import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.rentals.domain.command.CalculateRentalCommand
import java.math.BigDecimal
import java.time.temporal.ChronoUnit

@Component
class RentalPriceCalculator(private val priceConfiguration: PriceConfiguration) {

    fun calculate(command: CalculateRentalCommand): BigDecimal {
        val startDate = command.startDate
        val endDate = command.endDate

        val daysBetween = ChronoUnit.DAYS.between(startDate, endDate)
        val priceParDay = priceConfiguration.pricePerDay

        return daysBetween.toBigDecimal() * priceParDay
    }
}