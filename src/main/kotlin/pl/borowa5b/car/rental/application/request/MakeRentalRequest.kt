package pl.borowa5b.car.rental.application.request

import pl.borowa5b.car.rental.domain.command.MakeRentalCommand
import pl.borowa5b.car.rental.domain.exception.ValidationException
import pl.borowa5b.car.rental.domain.exception.validation.AggregatingValidationExceptionHandler
import pl.borowa5b.car.rental.domain.exception.validation.Validator
import pl.borowa5b.car.rental.domain.model.CarId
import pl.borowa5b.car.rental.domain.model.CustomerId
import java.time.OffsetDateTime

data class MakeRentalRequest(
    val carId: String?,
    val customerId: String?,
    val startDate: String?,
    val endDate: String?
) {

    fun validate() {
        val validationExceptionHandler = AggregatingValidationExceptionHandler()

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

        if (validationExceptionHandler.hasErrors()) {
            throw ValidationException(validationExceptionHandler.errors)
        }
    }

    fun toCommand(): MakeRentalCommand = MakeRentalCommand(
        CarId(carId!!),
        CustomerId(customerId!!),
        startDate = OffsetDateTime.parse(startDate!!),
        endDate = OffsetDateTime.parse(endDate!!)
    )
}