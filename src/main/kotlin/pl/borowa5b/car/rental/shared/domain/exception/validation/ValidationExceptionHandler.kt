package pl.borowa5b.car.rental.shared.domain.exception.validation

import pl.borowa5b.car.rental.shared.domain.exception.ValidationErrorException

interface ValidationExceptionHandler {

    fun handle(exception: ValidationErrorException)
}