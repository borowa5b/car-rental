package pl.borowa5b.car.rental.rentals.application.filter

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.cars.domain.shared.vo.ValueObjects.carId
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.customers.domain.shared.vo.ValueObjects.customerId
import pl.borowa5b.car.rental.rentals.application.filter.FilterObjects.getRentalsFilter
import pl.borowa5b.car.rental.rentals.domain.vo.RentalStatus
import pl.borowa5b.car.rental.shared.domain.exception.validation.AggregatingValidationExceptionHandler
import java.time.OffsetDateTime

class GetRentalsFilterTest {

    @Test
    fun `should validate filter`() {
        // given
        val validationExceptionHandler = AggregatingValidationExceptionHandler()
        val getRentalsFilter = getRentalsFilter(
            carId = carId().value,
            customerId = customerId().value,
            status = "STARTED",
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
            status = "wrongStatus",
            startDateFrom = "wrongStartDateFrom",
            startDateTo = "wrongStartDateTo",
            endDateFrom = "wrongEndDateFrom",
            endDateTo = "wrongEndDateTo"
        )

        // when
        getRentalsFilter.validate(validationExceptionHandler)

        // then
        assertThat(validationExceptionHandler.hasErrors()).isTrue()
        assertThat(validationExceptionHandler.errors).hasSize(7)
    }

    @Test
    fun `should convert to query`() {
        // given
        val dateTimeString = "2022-01-01T12:00:00Z"
        val carId = "CAR1234567890"
        val customerId = "CTR1234567890"
        val status = "STARTED"
        val getRentalsFilter = getRentalsFilter(
            carId = carId,
            customerId = customerId,
            status = status,
            startDateFrom = dateTimeString,
            startDateTo = dateTimeString,
            endDateFrom = dateTimeString,
            endDateTo = dateTimeString
        )

        // when
        val result = getRentalsFilter.toQuery()

        // then
        assertThat(result.carId).isEqualTo(CarId(carId))
        assertThat(result.customerId).isEqualTo(CustomerId(customerId))
        assertThat(result.status).isEqualTo(RentalStatus.valueOf(status))
        assertThat(result.startDateFrom).isEqualTo(OffsetDateTime.parse(dateTimeString))
        assertThat(result.startDateTo).isEqualTo(OffsetDateTime.parse(dateTimeString))
        assertThat(result.endDateFrom).isEqualTo(OffsetDateTime.parse(dateTimeString))
        assertThat(result.endDateTo).isEqualTo(OffsetDateTime.parse(dateTimeString))
    }
}