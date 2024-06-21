package pl.borowa5b.car.rental.rentals.application.request

import com.fasterxml.jackson.annotation.JsonCreator
import io.swagger.v3.oas.annotations.media.Schema
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.rentals.domain.command.MakeRentalCommand
import pl.borowa5b.car.rental.shared.domain.exception.validation.ValidationExceptionHandler
import pl.borowa5b.car.rental.shared.domain.exception.validation.Validator
import java.time.OffsetDateTime

data class MakeRentalRequest(
    @Schema(type = "string", description = "Car identifier", example = "CAR25433432342234")
    var carId: String?,
    @Schema(type = "string", description = "Customer identifier", example = "CTR25433432342234")
    var customerId: String?,
    @Schema(type = "string", format = "date-time", description = "Rental start date", example = "2025-01-01T12:00:00Z")
    var startDate: String?,
    @Schema(type = "string", format = "date-time", description = "Rental end date", example = "2025-01-02T12:00:00Z")
    var endDate: String?
) {

    @JsonCreator
    constructor() : this(null, null, null, null)

    fun validate(validationExceptionHandler: ValidationExceptionHandler) {
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