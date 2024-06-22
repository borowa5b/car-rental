package pl.borowa5b.car.rental.cars.application.request

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.cars.application.request.RequestObjects.editCarRequest
import pl.borowa5b.car.rental.cars.domain.command.EditCarCommand
import pl.borowa5b.car.rental.cars.domain.shared.vo.ValueObjects.carId
import pl.borowa5b.car.rental.cars.domain.vo.Brand
import pl.borowa5b.car.rental.shared.domain.exception.validation.AggregatingValidationExceptionHandler
import java.math.BigDecimal

class EditCarRequestTest {

    @Test
    fun `should validate request`() {
        // given
        val validationExceptionHandler = AggregatingValidationExceptionHandler()
        val request = editCarRequest()

        // when
        request.validate(validationExceptionHandler)

        // then
        assertThat(validationExceptionHandler.hasErrors()).isFalse()
    }

    @Test
    fun `should not validate request`() {
        // given
        val validationExceptionHandler = AggregatingValidationExceptionHandler()
        val request = editCarRequest(
            carId = carId().value,
            brand = "",
            model = "",
            generation = "",
            year = 1200,
            color = "",
            pricePerDay = BigDecimal.ZERO,
            quantity = -1
        )

        // when
        request.validate(validationExceptionHandler)

        // then
        assertThat(validationExceptionHandler.hasErrors()).isTrue()
        assertThat(validationExceptionHandler.errors).hasSize(7)
    }

    @Test
    fun `should convert to command`() {
        // given
        val request = editCarRequest(
            brand = Brand.TOYOTA.name,
            model = "Corolla",
            generation = "3",
            year = 2001,
            color = "sky blue",
            pricePerDay = BigDecimal.TEN,
            quantity = 10
        )

        // when
        val result = request.toCommand()

        // then
        assertThat(result).isExactlyInstanceOf(EditCarCommand::class.java)
        assertThat(result.carId.value).isEqualTo(request.carId)
        assertThat(result.brand!!.name).isEqualTo(request.brand)
        assertThat(result.model).isEqualTo(request.model!!.lowercase())
        assertThat(result.generation).isEqualTo(request.generation!!.lowercase())
        assertThat(result.year).isEqualTo(request.year)
        assertThat(result.color).isEqualTo(request.color!!.lowercase())
        assertThat(result.pricePerDay).isEqualTo(request.pricePerDay)
        assertThat(result.quantity).isEqualTo(request.quantity)
    }
}