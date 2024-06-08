package pl.borowa5b.car.rental.domain.model.vo

import pl.borowa5b.car.rental.domain.exception.ValidationErrorException
import pl.borowa5b.car.rental.domain.exception.validation.ThrowingValidationExceptionHandler
import pl.borowa5b.car.rental.domain.exception.validation.ValidationError
import pl.borowa5b.car.rental.domain.exception.validation.ValidationExceptionHandler

data class ExternalEventId(val value: String) {

    init {
        validate(value)
    }

    companion object {

        const val PREFIX: String = "EXT"

        fun validate(
            value: String,
            fieldName: String = "externalEventId",
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