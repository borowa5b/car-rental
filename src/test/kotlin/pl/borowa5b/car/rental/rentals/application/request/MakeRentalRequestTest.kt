package pl.borowa5b.car.rental.rentals.application.request

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.rentals.application.request.RequestObjects.makeRentalRequest
import pl.borowa5b.car.rental.rentals.domain.command.MakeRentalCommand
import pl.borowa5b.car.rental.shared.domain.exception.validation.AggregatingValidationExceptionHandler
import java.time.format.DateTimeFormatter

class MakeRentalRequestTest {

    @Test
    fun `should validate request`() {
        // given
        val validationExceptionHandler = AggregatingValidationExceptionHandler()
        val request = makeRentalRequest()

        // when
        request.validate(validationExceptionHandler)

        // then
        assertThat(validationExceptionHandler.hasErrors()).isFalse()
    }

    @Test
    fun `should not validate request`() {
        // given
        val validationExceptionHandler = AggregatingValidationExceptionHandler()
        val request = makeRentalRequest(
            carId = null,
            customerId = null,
            startDate = null,
            endDate = null
        )

        // when
        request.validate(validationExceptionHandler)

        // then
        assertThat(validationExceptionHandler.hasErrors()).isTrue()
        assertThat(validationExceptionHandler.errors).hasSize(4)
    }

    @Test
    fun `should convert to command`() {
        // given
        val request = makeRentalRequest()

        // when
        val result = request.toCommand()

        // then
        assertThat(result).isExactlyInstanceOf(MakeRentalCommand::class.java)
        assertThat(result.carId.value).isEqualTo(request.carId)
        assertThat(result.customerId.value).isEqualTo(request.customerId)
        assertThat(result.startDate.format(DateTimeFormatter.ISO_DATE_TIME)).isEqualTo(request.startDate)
        assertThat(result.endDate.format(DateTimeFormatter.ISO_DATE_TIME)).isEqualTo(request.endDate)
    }
}