package pl.borowa5b.car.rental.shared.domain.exception.validation

import pl.borowa5b.car.rental.shared.domain.exception.ValidationErrorException
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeParseException
import kotlin.enums.enumEntries

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
                        "Field $fieldName cannot be null",
                        fieldName
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
                        "Field $fieldName cannot be null or blank",
                        fieldName
                    )
                )
            )
        }
    }

    fun isNotBlank(
        value: String,
        fieldName: String,
        validationExceptionHandler: ValidationExceptionHandler = ThrowingValidationExceptionHandler()
    ) {
        if (value.isBlank()) {
            validationExceptionHandler.handle(
                ValidationErrorException(
                    ValidationError(
                        "Field is blank",
                        "Field $fieldName cannot be blank",
                        fieldName
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
                            "Date time $fieldName must be after $beforeDateTime",
                            fieldName
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
                            "Date time $fieldName must be in future",
                            fieldName
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
            } catch (_: DateTimeParseException) {
                validationExceptionHandler.handle(
                    ValidationErrorException(
                        ValidationError(
                            "Field has invalid value",
                            "Field $fieldName is not correctly formatted date time",
                            fieldName
                        )
                    )
                )
            }
        }
    }

    fun isGreaterThan(
        value: Number,
        greaterThan: Number,
        fieldName: String,
        validationExceptionHandler: ValidationExceptionHandler = ThrowingValidationExceptionHandler()
    ) {
        if (value.toDouble() <= greaterThan.toDouble()) {
            validationExceptionHandler.handle(
                ValidationErrorException(
                    ValidationError(
                        "Field has invalid value",
                        "Field $fieldName must be greater than $greaterThan",
                        fieldName
                    )
                )
            )
        }
    }

    fun isSmallerThan(
        value: Number,
        smallerThan: Number,
        fieldName: String,
        validationExceptionHandler: ValidationExceptionHandler = ThrowingValidationExceptionHandler()
    ) {
        if (value.toDouble() >= smallerThan.toDouble()) {
            validationExceptionHandler.handle(
                ValidationErrorException(
                    ValidationError(
                        "Field has invalid value",
                        "Field $fieldName must be smaller than $smallerThan",
                        fieldName
                    )
                )
            )
        }
    }

    inline fun <reified E : Enum<E>> isValidEnumValue(
        value: String,
        fieldName: String,
        validationExceptionHandler: ValidationExceptionHandler = ThrowingValidationExceptionHandler()
    ) {
        val enumEntries = enumEntries<E>()
        val enumValue = enumEntries.firstOrNull { it.name == value.uppercase() }
        if (enumValue == null) {
            validationExceptionHandler.handle(
                ValidationErrorException(
                    ValidationError(
                        "Field has invalid value",
                        "Field $fieldName has invalid value. Allowed value ${enumEntries.joinToString(", ")}",
                        fieldName
                    )
                )
            )
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