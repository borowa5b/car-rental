package pl.borowa5b.car.rental.application.filter

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.domain.exception.validation.AggregatingValidationExceptionHandler
import pl.borowa5b.car.rental.domain.model.DomainObjects.carId
import pl.borowa5b.car.rental.domain.model.DomainObjects.customerId

class GetRentalsFilterTest {

    @Test
    fun `should validate filter`() {
        // given
        val validationExceptionHandler = AggregatingValidationExceptionHandler()
        val getRentalsFilter = GetRentalsFilter(
            carId = carId().value,
            customerId = customerId().value,
            startDateFrom = "2022-01-01T12:00:00Z",
            startDateTo = "2022-01-01T12:00:00Z",
            endDateFrom = "2022-01-01T12:00:00Z",
            endDateTo = "2022-01-01T12:00:00Z"
        )

        // when
        getRentalsFilter.validate(validationExceptionHandler)

        // then
        assertThat(validationExceptionHandler.hasErrors()).isFalse()
    }

    @Test
    fun `should not validate filter`() {
        // given
        val validationExceptionHandler = AggregatingValidationExceptionHandler()
        val getRentalsFilter = GetRentalsFilter(
            carId = "wrongCarId",
            customerId = "wrongCustomerId",
            startDateFrom = "wrongStartDateFrom",
            startDateTo = "wrongStartDateTo",
            endDateFrom = "wrongEndDateFrom",
            endDateTo = "wrongEndDateTo"
        )

        // when
        getRentalsFilter.validate(validationExceptionHandler)

        // then
        assertThat(validationExceptionHandler.hasErrors()).isTrue()
        assertThat(validationExceptionHandler.errors).hasSize(6)
    }
}