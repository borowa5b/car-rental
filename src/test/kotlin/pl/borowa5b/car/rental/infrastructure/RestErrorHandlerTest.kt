package pl.borowa5b.car.rental.infrastructure

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import pl.borowa5b.car.rental.domain.DomainException
import pl.borowa5b.car.rental.domain.exception.ValidationErrorException
import pl.borowa5b.car.rental.domain.exception.ValidationException
import pl.borowa5b.car.rental.domain.exception.validation.ValidationError

class RestErrorHandlerTest {

    @Test
    fun `should handle unexpected error exception`() {
        // given
        val title = "Unexpected error"
        val message = "Unexpected error occurred"
        val exception = RuntimeException("example exception")
        val restErrorHandler = RestErrorHandler()

        // when
        val result = restErrorHandler.handle(exception)

        // then
        assertThat(result.statusCode).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)

        val problem = result.body!!
        assertThat(problem.status?.statusCode).isEqualTo(result.statusCode.value())
        assertThat(problem.title).isEqualTo(title)
        assertThat(problem.detail).isEqualTo(message)
    }

    @Test
    fun `should handle validation error exception`() {
        // given
        val title = "Field has invalid value"
        val message = "Field `carId` must start with `CAR`"
        val exception = ValidationErrorException(ValidationError(title, message))
        val restErrorHandler = RestErrorHandler()

        // when
        val result = restErrorHandler.handle(exception)

        // then
        assertThat(result.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)

        val problem = result.body!!
        assertThat(problem.status?.statusCode).isEqualTo(result.statusCode.value())
        assertThat(problem.title).isEqualTo(title)
        assertThat(problem.detail).isEqualTo(message)
    }

    @Test
    fun `should handle validation exception`() {
        // given
        val title = "Validation errors"
        val message = "Validation errors occurred"
        val validationError1 = ValidationError("Field has invalid value", "Field `carId` must start with `CAR`")
        val validationError2 = ValidationError("Field has invalid value", "Field `customerId` must start with `CTR`")
        val errors = listOf(validationError1, validationError2)
        val exception = ValidationException(errors)
        val restErrorHandler = RestErrorHandler()

        // when
        val result = restErrorHandler.handle(exception)

        // then
        assertThat(result.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)

        val problem = result.body!!
        assertThat(problem.status?.statusCode).isEqualTo(result.statusCode.value())
        assertThat(problem.title).isEqualTo(title)
        assertThat(problem.detail).isEqualTo(message)

        val problemErrors = problem.parameters["errors"] as List<*>
        assertThat(problemErrors).hasSize(2)
        assertThat(problemErrors[0]).isEqualTo(validationError1)
        assertThat(problemErrors[1]).isEqualTo(validationError2)
    }

    @Test
    fun `should handle domain exception`() {
        // given
        val title = "Business error"
        val message = "Business error occurred"
        val exception = DomainException(message)
        val restErrorHandler = RestErrorHandler()

        // when
        val result = restErrorHandler.handle(exception)

        // then
        assertThat(result.statusCode).isEqualTo(HttpStatus.CONFLICT)

        val problem = result.body!!
        assertThat(problem.status?.statusCode).isEqualTo(result.statusCode.value())
        assertThat(problem.title).isEqualTo(title)
        assertThat(problem.detail).isEqualTo(message)
    }
}