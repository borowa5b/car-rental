package pl.borowa5b.car.rental.domain.model

import pl.borowa5b.car.rental.domain.exception.ValidationErrorException
import pl.borowa5b.car.rental.domain.exception.validation.ThrowingValidationExceptionHandler
import pl.borowa5b.car.rental.domain.exception.validation.ValidationError
import pl.borowa5b.car.rental.domain.exception.validation.ValidationExceptionHandler

data class CarId(val value: String) {

    init {
        validate(value)
    }

    companion object {

        const val PREFIX: String = "CAR"

        fun validate(
            value: String,
            fieldName: String = "carId",
            validationExceptionHandler: ValidationExceptionHandler = ThrowingValidationExceptionHandler()
        ) {
            if (!value.startsWith(PREFIX)) {
                validationExceptionHandler.handle(
                    ValidationErrorException(
                        ValidationError(
                            "Field has invalid value",
                            "Field `$fieldName` must start with `$PREFIX`"
                        )
                    )
                )
            }
        }
    }
}