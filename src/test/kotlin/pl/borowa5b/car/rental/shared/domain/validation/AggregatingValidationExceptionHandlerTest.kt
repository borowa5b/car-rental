package pl.borowa5b.car.rental.shared.domain.validation

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.shared.domain.exception.ValidationErrorException
import pl.borowa5b.car.rental.shared.domain.exception.validation.AggregatingValidationExceptionHandler
import pl.borowa5b.car.rental.shared.domain.exception.validation.ValidationError

class AggregatingValidationExceptionHandlerTest {

    @Test
    fun `should aggregate validation errors`() {
        // given
        val handler = AggregatingValidationExceptionHandler()
        val validationError = ValidationError("test", "test", "test")
        val exception = ValidationErrorException(validationError)

        // when
        handler.handle(exception)
        handler.handle(exception)

        // then
        assertThat(handler.hasErrors()).isTrue()
        assertThat(handler.errors).hasSize(2)
        assertThat(handler.errors).allMatch { it == validationError }
    }
}