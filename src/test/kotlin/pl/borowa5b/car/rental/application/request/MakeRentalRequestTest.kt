package pl.borowa5b.car.rental.application.request

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.application.request.RequestObjects.makeRentalRequest
import pl.borowa5b.car.rental.domain.command.MakeRentalCommand
import pl.borowa5b.car.rental.domain.exception.ValidationException
import java.time.format.DateTimeFormatter

class MakeRentalRequestTest {

    @Test
    fun `should validate request`() {
        // given
        val request = makeRentalRequest()

        // when
        val result = catchThrowable { request.validate() }

        // then
        assertThat(result).isNull()
    }

    @Test
    fun `should not validate request`() {
        // given
        val request = makeRentalRequest(
            carId = null,
            customerId = null,
            startDate = null,
            endDate = null
        )

        // when
        val result = catchThrowable { request.validate() }

        // then
        assertThat(result).isExactlyInstanceOf(ValidationException::class.java)

        val validationException = result as ValidationException
        assertThat(validationException.errors).hasSize(4)
    }

    @Test
    fun `should convert request to command`() {
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