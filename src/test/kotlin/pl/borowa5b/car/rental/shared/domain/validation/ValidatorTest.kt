package pl.borowa5b.car.rental.shared.domain.validation

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.shared.domain.exception.ValidationErrorException
import pl.borowa5b.car.rental.shared.domain.exception.validation.Validator

class ValidatorTest {

    @Test
    fun `should not validate if not null`() {
        // given
        val value = null
        val fieldName = "fieldName"

        // when
        val result = catchThrowable { Validator.isNotNull(value, fieldName) }

        // then
        assertThat(result).isExactlyInstanceOf(ValidationErrorException::class.java)
    }

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
    fun `should not validate if blank`() {
        // given
        val value = ""
        val fieldName = "fieldName"

        // when
        val result = catchThrowable { Validator.isNotBlank(value, fieldName) }

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

    @Test
    fun `should not validate if date time is not correctly formatted`() {
        // given
        val value = "2020-01-01Time00:00:00"
        val fieldName = "fieldName"

        // when
        val result = catchThrowable { Validator.isValidOffsetDateTime(value, fieldName) }

        // then
        assertThat(result).isExactlyInstanceOf(ValidationErrorException::class.java)
    }

    @Test
    fun `should not validate if number is not greater than`() {
        // given
        val value = 1.50
        val greaterThan = 2.50
        val fieldName = "fieldName"

        // when
        val result = catchThrowable { Validator.isGreaterThan(value, greaterThan, fieldName) }

        // then
        assertThat(result).isExactlyInstanceOf(ValidationErrorException::class.java)
    }

    @Test
    fun `should not validate if number is not smaller than`() {
        // given
        val value = 3.50
        val smallerThan = 2.50
        val fieldName = "fieldName"

        // when
        val result = catchThrowable { Validator.isSmallerThan(value, smallerThan, fieldName) }

        // then
        assertThat(result).isExactlyInstanceOf(ValidationErrorException::class.java)
    }

    @Test
    fun `should not validate if value is not from enum`() {
        // given
        val value = "value"
        val fieldName = "fieldName"

        // when
        val result = catchThrowable { Validator.isValidEnumValue<TestEnum>(value, fieldName) }

        // then
        assertThat(result).isExactlyInstanceOf(ValidationErrorException::class.java)
    }

    private enum class TestEnum {
        TEST_VALUE
    }
}