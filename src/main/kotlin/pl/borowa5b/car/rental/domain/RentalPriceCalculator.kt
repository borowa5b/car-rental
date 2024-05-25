package pl.borowa5b.car.rental.domain

import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

@Component
class RentalPriceCalculator(private val priceConfiguration: PriceConfiguration) {

    fun calculate(startDate: OffsetDateTime, endDate: OffsetDateTime): BigDecimal {
        val daysBetween = ChronoUnit.DAYS.between(startDate, endDate)
        val priceParDay = priceConfiguration.pricePerDay

        return daysBetween.toBigDecimal() * priceParDay
    }
}