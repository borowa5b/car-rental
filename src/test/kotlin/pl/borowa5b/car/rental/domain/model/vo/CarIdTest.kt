package pl.borowa5b.car.rental.domain.model.vo

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.domain.exception.ValidationErrorException

class CarIdTest {

    @Test
    fun `should validate car id`() {
        // given

        // when
        val result = CarId("CAR123")

        // then
        assertThat(result).isNotNull()
    }

    @Test
    fun `should not validate car id`() {
        // given

        // when
        val result = catchThrowable { CarId("123") }

        // then
        assertThat(result).isExactlyInstanceOf(ValidationErrorException::class.java)
    }
}