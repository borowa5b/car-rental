package pl.borowa5b.car.rental.application.request

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.application.request.RequestObjects.calculateRentalRequest
import pl.borowa5b.car.rental.domain.exception.validation.AggregatingValidationExceptionHandler
import java.time.format.DateTimeFormatter

class CalculateRentalRequestTest {

    @Test
    fun `should validate request`() {
        // given
        val validationExceptionHandler = AggregatingValidationExceptionHandler()
        val request = calculateRentalRequest()

        // when
        request.validate(validationExceptionHandler)

        // then
        assertThat(validationExceptionHandler.hasErrors()).isFalse()
    }

    @Test
    fun `should not validate request`() {
        // given
        val validationExceptionHandler = AggregatingValidationExceptionHandler()
        val request = calculateRentalRequest(
            startDate = "2020-01-01T00:00:00",
            endDate = ""
        )

        // when
        request.validate(validationExceptionHandler)

        // then
        assertThat(validationExceptionHandler.hasErrors()).isTrue()
        assertThat(validationExceptionHandler.errors).hasSize(2)
    }

    @Test
    fun `should convert to command`() {
        // given
        val request = calculateRentalRequest()

        // when
        val result = request.toCommand()

        // then
        assertThat(result).isExactlyInstanceOf(pl.borowa5b.car.rental.domain.command.CalculateRentalCommand::class.java)
        assertThat(result.startDate.format(DateTimeFormatter.ISO_DATE_TIME)).isEqualTo(request.startDate)
        assertThat(result.endDate.format(DateTimeFormatter.ISO_DATE_TIME)).isEqualTo(request.endDate)
    }
}