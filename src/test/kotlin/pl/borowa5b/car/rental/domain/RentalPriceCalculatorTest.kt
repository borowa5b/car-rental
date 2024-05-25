package pl.borowa5b.car.rental.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.time.ZoneOffset

class RentalPriceCalculatorTest {

    @Test
    fun `should calculate rental price`() {
        // given
        val pricePerDay = BigDecimal.TEN
        val rentalPriceCalculator = RentalPriceCalculator(TestPriceConfiguration(pricePerDay))

        // when
        val result = rentalPriceCalculator.calculate(OffsetDateTime.now(ZoneOffset.UTC), OffsetDateTime.now(ZoneOffset.UTC).plusDays(1))

        // then
        assertThat(result).isEqualTo(pricePerDay)
    }

    private class TestPriceConfiguration(override val pricePerDay: BigDecimal) : PriceConfiguration
}