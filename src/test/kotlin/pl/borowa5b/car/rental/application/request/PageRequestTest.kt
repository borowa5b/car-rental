package pl.borowa5b.car.rental.application.request

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.domain.exception.validation.AggregatingValidationExceptionHandler

class PageRequestTest {

    @Test
    fun `should validate request`() {
        // given
        val validationExceptionHandler = AggregatingValidationExceptionHandler()
        val pageRequest = PageRequest(
            number = 1,
            size = 100,
            order = "creationDate,DESC"
        )

        // when
        pageRequest.validate(validationExceptionHandler)

        // then
        assertThat(validationExceptionHandler.hasErrors()).isFalse()
    }

    @Test
    fun `should not validate request`() {
        // given
        val validationExceptionHandler = AggregatingValidationExceptionHandler()
        val pageRequest = PageRequest(
            number = null,
            size = null,
            order = null
        )

        // when
        pageRequest.validate(validationExceptionHandler)

        // then
        assertThat(validationExceptionHandler.hasErrors()).isTrue()
        assertThat(validationExceptionHandler.errors).hasSize(3)
    }
}