package pl.borowa5b.car.rental.shared.domain.exception.validation

import pl.borowa5b.car.rental.shared.domain.exception.ValidationErrorException
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeParseException

object Validator {

    fun isNotNull(
        value: Any?,
        fieldName: String,
        validationExceptionHandler: ValidationExceptionHandler = ThrowingValidationExceptionHandler()
    ) {
        if (value == null) {
            validationExceptionHandler.handle(
                ValidationErrorException(
                    ValidationError(
                        "Field is null",
                        "Field $fieldName cannot be null"
                    )
                )
            )
        }
    }

    fun isNotNullOrBlank(
        value: String?,
        fieldName: String,
        validationExceptionHandler: ValidationExceptionHandler = ThrowingValidationExceptionHandler()
    ) {
        if (value.isNullOrBlank()) {
            validationExceptionHandler.handle(
                ValidationErrorException(
                    ValidationError(
                        "Field is null or blank",
                        "Field $fieldName cannot be null or blank"
                    )
                )
            )
        }
    }

    fun isAfter(
        value: String?,
        beforeDateTime: String?,
        fieldName: String,
        validationExceptionHandler: ValidationExceptionHandler = ThrowingValidationExceptionHandler()
    ) {
        isValidOffsetDateTime(value, fieldName, validationExceptionHandler)

        val parsedValue = parseOffsetDateTime(value)
        val parsedBeforeDateTime = parseOffsetDateTime(beforeDateTime)
        if (parsedValue != null && parsedBeforeDateTime != null) {
            if (!parsedValue.isAfter(parsedBeforeDateTime)) {
                validationExceptionHandler.handle(
                    ValidationErrorException(
                        ValidationError(
                            "Field is before date time",
                            "Date time $fieldName must be after $beforeDateTime"
                        )
                    )
                )
            }
        }
    }

    fun isInFuture(
        value: String?,
        fieldName: String,
        validationExceptionHandler: ValidationExceptionHandler = ThrowingValidationExceptionHandler()
    ) {
        isValidOffsetDateTime(value, fieldName, validationExceptionHandler)

        parseOffsetDateTime(value)?.let {
            if (!it.isAfter(OffsetDateTime.now(ZoneOffset.UTC))) {
                validationExceptionHandler.handle(
                    ValidationErrorException(
                        ValidationError(
                            "Field is before current date time",
                            "Date time $fieldName must be in future"
                        )
                    )
                )
            }
        }
    }

    fun isValidOffsetDateTime(
        value: String?,
        fieldName: String,
        validationExceptionHandler: ValidationExceptionHandler = ThrowingValidationExceptionHandler()
    ) {
        isNotNullOrBlank(value, fieldName, validationExceptionHandler)

        value?.let {
            if (it.isBlank()) {
                return
            }

            try {
                OffsetDateTime.parse(value)
            } catch (exception: DateTimeParseException) {
                validationExceptionHandler.handle(
                    ValidationErrorException(
                        ValidationError(
                            "Field has invalid value",
                            "Field $fieldName is not correctly formatted date time"
                        )
                    )
                )
            }
        }
    }

    private fun parseOffsetDateTime(value: String?) = value?.let {
        try {
            OffsetDateTime.parse(value)
        } catch (exception: DateTimeParseException) {
            null
        }
    }
}