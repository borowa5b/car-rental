package pl.borowa5b.car.rental.shared.domain.pagination

import org.springframework.data.domain.Sort
import pl.borowa5b.car.rental.shared.domain.exception.ValidationErrorException
import pl.borowa5b.car.rental.shared.domain.exception.validation.ThrowingValidationExceptionHandler
import pl.borowa5b.car.rental.shared.domain.exception.validation.ValidationError
import pl.borowa5b.car.rental.shared.domain.exception.validation.ValidationExceptionHandler

object OrderParser {

    private val ORDER_REGEX = Regex(".*,(ASC|DESC)")

    fun parse(order: String): Sort.Order {
        validate(order)
        val (property, direction) = order.split(",")
        return Sort.Order(Sort.Direction.fromString(direction), property)
    }

    fun validate(
        order: String?,
        fieldName: String = "order",
        validationExceptionHandler: ValidationExceptionHandler = ThrowingValidationExceptionHandler()
    ) {
        order?.let {
            if (!ORDER_REGEX.matches(it)) {
                validationExceptionHandler.handle(
                    ValidationErrorException(
                        ValidationError(
                            "Invalid page order",
                            "Page order field `$fieldName` must be in format 'property,direction(ASC|DESC)'",
                            fieldName
                        )
                    )
                )
            }
        }
    }
}