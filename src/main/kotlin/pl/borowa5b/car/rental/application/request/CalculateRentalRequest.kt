package pl.borowa5b.car.rental.application.request

import io.swagger.v3.oas.annotations.media.Schema
import pl.borowa5b.car.rental.domain.command.CalculateRentalCommand
import pl.borowa5b.car.rental.domain.exception.validation.ValidationExceptionHandler
import pl.borowa5b.car.rental.domain.exception.validation.Validator
import java.time.OffsetDateTime

data class CalculateRentalRequest(
    @Schema(type = "string", format = "date-time", description = "Rental start date", example = "2025-01-01T12:00:00Z")
    val startDate: String?,
    @Schema(type = "string", format = "date-time", description = "Rental end date", example = "2025-01-02T12:00:00Z")
    val endDate: String?
) {

    fun validate(validationExceptionHandler: ValidationExceptionHandler) {
        Validator.isInFuture(startDate, "startDate", validationExceptionHandler)
        Validator.isAfter(endDate, startDate, "endDate", validationExceptionHandler)
    }

    fun toCommand(): CalculateRentalCommand = CalculateRentalCommand(
        startDate = OffsetDateTime.parse(startDate!!),
        endDate = OffsetDateTime.parse(endDate!!)
    )
}