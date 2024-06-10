package pl.borowa5b.car.rental.rentals.application.filter

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.cars.domain.shared.vo.ValueObjects.carId
import pl.borowa5b.car.rental.customers.domain.shared.vo.ValueObjects.customerId
import pl.borowa5b.car.rental.rentals.application.filter.FilterObjects.getRentalsFilter
import pl.borowa5b.car.rental.shared.domain.exception.validation.AggregatingValidationExceptionHandler

class GetRentalsFilterTest {

    @Test
    fun `should validate filter`() {
        // given
        val validationExceptionHandler = AggregatingValidationExceptionHandler()
        val getRentalsFilter = getRentalsFilter(
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
        val getRentalsFilter = getRentalsFilter(
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