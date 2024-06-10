package pl.borowa5b.car.rental.shared.domain.exception

import pl.borowa5b.car.rental.shared.domain.exception.validation.ValidationError

class ValidationErrorException(val error: ValidationError) : RuntimeException()
