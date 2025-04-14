package pl.borowa5b.car.rental.events.domain.vo

import com.fasterxml.jackson.annotation.JsonProperty
import pl.borowa5b.car.rental.shared.domain.exception.ValidationErrorException
import pl.borowa5b.car.rental.shared.domain.exception.validation.ThrowingValidationExceptionHandler
import pl.borowa5b.car.rental.shared.domain.exception.validation.ValidationError
import pl.borowa5b.car.rental.shared.domain.exception.validation.ValidationExceptionHandler

data class EventId(
    @JsonProperty("id")
    val value: String
) {

    init {
        validate(value)
    }

    companion object {

        const val PREFIX: String = "EVT"

        fun validate(
            value: String,
            fieldName: String = "eventId",
            validationExceptionHandler: ValidationExceptionHandler = ThrowingValidationExceptionHandler()
        ) {
            if (!value.startsWith(PREFIX)) {
                validationExceptionHandler.handle(
                    ValidationErrorException(
                        ValidationError(
                            "Field has invalid value",
                            "Field `$fieldName` must start with `$PREFIX`",
                            fieldName
                        )
                    )
                )
            }
        }
    }
}