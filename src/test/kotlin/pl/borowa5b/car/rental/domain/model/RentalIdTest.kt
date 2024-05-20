package pl.borowa5b.car.rental.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test

class RentalIdTest {

    @Test
    fun `should validate rental id`() {
        // given

        // when
        val result = RentalId("RNT123")

        // then
        assertThat(result).isNotNull()
    }

    @Test
    fun `should not validate rental id`() {
        // given

        // when
        val result = catchThrowable { RentalId("123") }

        // then
        assertThat(result).isExactlyInstanceOf(IllegalArgumentException::class.java)
    }
}