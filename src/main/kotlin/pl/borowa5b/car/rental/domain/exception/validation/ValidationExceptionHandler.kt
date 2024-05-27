package pl.borowa5b.car.rental.domain.exception.validation

import pl.borowa5b.car.rental.domain.exception.ValidationErrorException

interface ValidationExceptionHandler {

    fun handle(exception: ValidationErrorException)
}