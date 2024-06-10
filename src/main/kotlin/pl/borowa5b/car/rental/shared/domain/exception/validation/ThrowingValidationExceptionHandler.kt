package pl.borowa5b.car.rental.shared.domain.exception.validation

import pl.borowa5b.car.rental.shared.domain.exception.ValidationErrorException

class ThrowingValidationExceptionHandler : ValidationExceptionHandler {

    override fun handle(exception: ValidationErrorException) = throw exception
}