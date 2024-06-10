package pl.borowa5b.car.rental.rentals.application.request

import io.swagger.v3.oas.annotations.media.Schema
import pl.borowa5b.car.rental.rentals.domain.command.MakeRentalCommand
import pl.borowa5b.car.rental.shared.domain.exception.validation.AggregatingValidationExceptionHandler
import pl.borowa5b.car.rental.shared.domain.exception.validation.Validator
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import java.time.OffsetDateTime

data class MakeRentalRequest(
    @Schema(type = "string", description = "Car identifier", example = "CAR25433432342234")
    val carId: String?,
    @Schema(type = "string", description = "Customer identifier", example = "CTR25433432342234")
    val customerId: String?,
    @Schema(type = "string", format = "date-time", description = "Rental start date", example = "2025-01-01T12:00:00Z")
    val startDate: String?,
    @Schema(type = "string", format = "date-time", description = "Rental end date", example = "2025-01-02T12:00:00Z")
    val endDate: String?
) {

    fun validate(validationExceptionHandler: AggregatingValidationExceptionHandler) {
        Validator.isNotNullOrBlank(carId, "carId", validationExceptionHandler)
        Validator.isNotNullOrBlank(customerId, "carId", validationExceptionHandler)
        Validator.isInFuture(startDate, "startDate", validationExceptionHandler)
        Validator.isAfter(endDate, startDate, "endDate", validationExceptionHandler)


        carId?.let {
            CarId.validate(it, validationExceptionHandler = validationExceptionHandler)
        }

        customerId?.let {
            CustomerId.validate(it, validationExceptionHandler = validationExceptionHandler)
        }
    }

    fun toCommand(): MakeRentalCommand = MakeRentalCommand(
        CarId(carId!!),
        CustomerId(customerId!!),
        startDate = OffsetDateTime.parse(startDate!!),
        endDate = OffsetDateTime.parse(endDate!!)
    )
}