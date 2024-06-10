package pl.borowa5b.car.rental.rentals.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.rentals.domain.command.CommandObjects.calculateRentalCommand
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.time.ZoneOffset

class RentalPriceCalculatorTest {

    @Test
    fun `should calculate rental price`() {
        // given
        val pricePerDay = BigDecimal.TEN
        val rentalPriceCalculator = RentalPriceCalculator(TestPriceConfiguration(pricePerDay))
        val command = calculateRentalCommand(
            startDate = OffsetDateTime.now(ZoneOffset.UTC),
            endDate = OffsetDateTime.now(ZoneOffset.UTC).plusDays(1)
        )

        // when
        val result = rentalPriceCalculator.calculate(command)

        // then
        assertThat(result).isEqualTo(pricePerDay)
    }

    private class TestPriceConfiguration(override val pricePerDay: BigDecimal) : PriceConfiguration
}