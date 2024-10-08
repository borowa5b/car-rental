package pl.borowa5b.car.rental.cars.application.request

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.cars.application.request.RequestObjects.addCarRequest
import pl.borowa5b.car.rental.cars.domain.command.AddCarCommand
import pl.borowa5b.car.rental.shared.domain.exception.validation.AggregatingValidationExceptionHandler

class AddCarRequestTest {

    @Test
    fun `should validate request`() {
        // given
        val validationExceptionHandler = AggregatingValidationExceptionHandler()
        val request = addCarRequest()

        // when
        request.validate(validationExceptionHandler)

        // then
        assertThat(validationExceptionHandler.hasErrors()).isFalse()
    }

    @Test
    fun `should not validate request`() {
        // given
        val validationExceptionHandler = AggregatingValidationExceptionHandler()
        val request = addCarRequest(
            brand = "",
            model = "",
            generation = "",
            productionYear = null,
            color = "",
            pricePerDay = null,
            quantity = null
        )

        // when
        request.validate(validationExceptionHandler)

        // then
        assertThat(validationExceptionHandler.hasErrors()).isTrue()
        assertThat(validationExceptionHandler.errors).hasSize(8)
    }

    @Test
    fun `should convert to command`() {
        // given
        val request = addCarRequest()

        // when
        val result = request.toCommand()

        // then
        assertThat(result).isExactlyInstanceOf(AddCarCommand::class.java)
        assertThat(result.brand.name).isEqualTo(request.brand)
        assertThat(result.getModel()).isEqualTo(request.model!!.lowercase())
        assertThat(result.getGeneration()).isEqualTo(request.generation!!.lowercase())
        assertThat(result.productionYear).isEqualTo(request.productionYear)
        assertThat(result.getColor()).isEqualTo(request.color!!.lowercase())
        assertThat(result.pricePerDay).isEqualTo(request.pricePerDay)
        assertThat(result.quantity).isEqualTo(request.quantity)
    }
}