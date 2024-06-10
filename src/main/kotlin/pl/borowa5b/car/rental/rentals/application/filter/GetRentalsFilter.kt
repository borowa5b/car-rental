package pl.borowa5b.car.rental.rentals.application.filter

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import org.springdoc.core.annotations.ParameterObject
import pl.borowa5b.car.rental.shared.domain.exception.validation.ValidationExceptionHandler
import pl.borowa5b.car.rental.shared.domain.exception.validation.Validator
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.rentals.domain.repository.read.RentalQuery
import java.time.OffsetDateTime

@ParameterObject
data class GetRentalsFilter(
    @Parameter(description = "Car identifier", example = "CAR1214242443242")
    val carId: String? = null,

    @Parameter(description = "Customer identifier", example = "CTR1214242443242")
    val customerId: String? = null,

    @Parameter(description = "Rental start date from", example = "2022-01-01T12:00:00Z")
    val startDateFrom: String? = null,

    @Parameter(description = "Rental start date to", example = "2022-01-01T12:00:00Z")
    val startDateTo: String? = null,

    @Parameter(description = "Rental end date from", example = "2022-01-01T12:00:00Z")
    val endDateFrom: String? = null,

    @Schema(description = "Rental end date to", example = "2022-01-01T12:00:00Z")
    val endDateTo: String? = null
) {

    fun validate(validationExceptionHandler: ValidationExceptionHandler) {
        carId?.let {
            CarId.validate(it, "carId", validationExceptionHandler)
        }
        customerId?.let {
            CustomerId.validate(it, "customerId", validationExceptionHandler)
        }
        startDateFrom?.let {
            Validator.isValidOffsetDateTime(it, "startDateFrom", validationExceptionHandler)
        }
        startDateTo?.let {
            Validator.isValidOffsetDateTime(it, "startDateTo", validationExceptionHandler)
        }
        endDateFrom?.let {
            Validator.isValidOffsetDateTime(it, "endDateFrom", validationExceptionHandler)
        }
        endDateTo?.let {
            Validator.isValidOffsetDateTime(it, "endDateTo", validationExceptionHandler)
        }
    }

    fun toQuery(): RentalQuery = RentalQuery(
        carId = carId?.let { CarId(it) },
        customerId = customerId?.let { CustomerId(it) },
        startDateFrom = startDateFrom?.let { OffsetDateTime.parse(it) },
        startDateTo = startDateTo?.let { OffsetDateTime.parse(it) },
        endDateFrom = endDateFrom?.let { OffsetDateTime.parse(it) },
        endDateTo = endDateTo?.let { OffsetDateTime.parse(it) }
    )
}