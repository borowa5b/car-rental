package pl.borowa5b.car.rental.application.request

import pl.borowa5b.car.rental.domain.command.CalculateRentalCommand
import pl.borowa5b.car.rental.domain.exception.ValidationException
import pl.borowa5b.car.rental.domain.exception.validation.AggregatingValidationExceptionHandler
import pl.borowa5b.car.rental.domain.exception.validation.Validator
import java.time.OffsetDateTime

data class CalculateRentalRequest(
    val startDate: String?,
    val endDate: String?
) {

    fun validate() {
        val validationExceptionHandler = AggregatingValidationExceptionHandler()

        Validator.isInFuture(startDate, "startDate", validationExceptionHandler)
        Validator.isAfter(endDate, startDate, "endDate", validationExceptionHandler)

        if (validationExceptionHandler.hasErrors()) {
            throw ValidationException(validationExceptionHandler.errors)
        }
    }

    fun toCommand(): CalculateRentalCommand = CalculateRentalCommand(
        startDate = OffsetDateTime.parse(startDate!!),
        endDate = OffsetDateTime.parse(endDate!!)
    )
}