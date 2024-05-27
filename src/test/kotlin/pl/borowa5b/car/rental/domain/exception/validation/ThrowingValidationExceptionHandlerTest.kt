package pl.borowa5b.car.rental.domain.exception.validation

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.domain.exception.ValidationErrorException

class ThrowingValidationExceptionHandlerTest {

    @Test
    fun `should throw exception`() {
        // given
        val handler = ThrowingValidationExceptionHandler()
        val exception = ValidationErrorException(ValidationError("title", "message"))


        // when
        val result = catchThrowable { handler.handle(exception) }

        // then
        assertThat(result).isEqualTo(exception)
    }
}