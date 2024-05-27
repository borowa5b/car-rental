package pl.borowa5b.car.rental.domain.exception.validation

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.domain.exception.ValidationErrorException

class ValidatorTest {

    @Test
    fun `should not validate if null or blank`() {
        // given
        val value = null
        val fieldName = "fieldName"

        // when
        val result = catchThrowable { Validator.isNotNullOrBlank(value, fieldName) }

        // then
        assertThat(result).isExactlyInstanceOf(ValidationErrorException::class.java)
    }

    @Test
    fun `should not validate if date time is not after other date time`() {
        // given
        val value = "2020-01-01T00:00:00"
        val beforeDateTime = "2020-01-01T00:00:01"
        val fieldName = "fieldName"

        // when
        val result = catchThrowable { Validator.isAfter(value, beforeDateTime, fieldName) }

        // then
        assertThat(result).isExactlyInstanceOf(ValidationErrorException::class.java)
    }

    @Test
    fun `should not validate if date time is not in future`() {
        // given
        val value = "2020-01-01T00:00:00"
        val fieldName = "fieldName"

        // when
        val result = catchThrowable { Validator.isInFuture(value, fieldName) }

        // then
        assertThat(result).isExactlyInstanceOf(ValidationErrorException::class.java)
    }
}