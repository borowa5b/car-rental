package pl.borowa5b.car.rental.shared.application.request

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.shared.domain.exception.validation.AggregatingValidationExceptionHandler

class PageRequestTest {

    @Test
    fun `should validate request`() {
        // given
        val validationExceptionHandler = AggregatingValidationExceptionHandler()
        val pageRequest = PageRequest(
            pageNumber = 1,
            pageSize = 100,
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
            pageNumber = null,
            pageSize = null,
            order = null
        )

        // when
        pageRequest.validate(validationExceptionHandler)

        // then
        assertThat(validationExceptionHandler.hasErrors()).isTrue()
        assertThat(validationExceptionHandler.errors).hasSize(3)
    }
}