package pl.borowa5b.car.rental.shared.domain.exception.validation

import pl.borowa5b.car.rental.shared.domain.exception.ValidationErrorException

class AggregatingValidationExceptionHandler(val errors: ArrayList<ValidationError> = ArrayList()) :
    ValidationExceptionHandler {

    override fun handle(exception: ValidationErrorException) {
        errors.add(exception.error)
    }

    fun hasErrors() = errors.isNotEmpty()
}