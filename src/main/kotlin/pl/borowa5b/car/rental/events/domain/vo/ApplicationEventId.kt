package pl.borowa5b.car.rental.events.domain.vo

import pl.borowa5b.car.rental.shared.domain.exception.ValidationErrorException
import pl.borowa5b.car.rental.shared.domain.exception.validation.ThrowingValidationExceptionHandler
import pl.borowa5b.car.rental.shared.domain.exception.validation.ValidationError
import pl.borowa5b.car.rental.shared.domain.exception.validation.ValidationExceptionHandler

data class ApplicationEventId(val value: String) {

    init {
        validate(value)
    }

    companion object {

        const val PREFIX: String = "AEV"

        fun validate(
            value: String,
            fieldName: String = "applicationEventId",
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