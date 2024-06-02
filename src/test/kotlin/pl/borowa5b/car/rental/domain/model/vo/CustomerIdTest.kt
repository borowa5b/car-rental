package pl.borowa5b.car.rental.domain.model.vo

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.domain.exception.ValidationErrorException

class CustomerIdTest {

    @Test
    fun `should validate customer id`() {
        // given

        // when
        val result = CustomerId("CTR123")

        // then
        assertThat(result).isNotNull()
    }

    @Test
    fun `should not validate customer id`() {
        // given

        // when
        val result = catchThrowable { CustomerId("123") }

        // then
        assertThat(result).isExactlyInstanceOf(ValidationErrorException::class.java)
    }
}