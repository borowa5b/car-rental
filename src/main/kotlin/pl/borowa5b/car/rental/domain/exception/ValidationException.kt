package pl.borowa5b.car.rental.domain.exception

import pl.borowa5b.car.rental.domain.exception.validation.ValidationError

class ValidationException(val errors: List<ValidationError>) : RuntimeException()
