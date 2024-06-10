package pl.borowa5b.car.rental.customers.domain.shared.vo

import pl.borowa5b.car.rental.shared.domain.exception.ValidationErrorException
import pl.borowa5b.car.rental.shared.domain.exception.validation.ThrowingValidationExceptionHandler
import pl.borowa5b.car.rental.shared.domain.exception.validation.ValidationError
import pl.borowa5b.car.rental.shared.domain.exception.validation.ValidationExceptionHandler

data class CustomerId(val value: String) {

    init {
        validate(value)
    }

    companion object {

        const val PREFIX: String = "CTR"

        fun validate(
            value: String,
            fieldName: String = "customerId",
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