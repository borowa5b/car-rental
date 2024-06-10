package pl.borowa5b.car.rental.rentals.domain.vo

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.shared.domain.exception.ValidationErrorException

class RentalIdTest {

    @Test
    fun `should validate rental id`() {
        // given

        // when
        val result = RentalId("RNL123")

        // then
        assertThat(result).isNotNull()
    }

    @Test
    fun `should not validate rental id`() {
        // given

        // when
        val result = catchThrowable { RentalId("123") }

        // then
        assertThat(result).isExactlyInstanceOf(ValidationErrorException::class.java)
    }
}