package pl.borowa5b.car.rental.shared.domain.pagination

import org.springframework.data.domain.Sort
import pl.borowa5b.car.rental.shared.domain.exception.ValidationErrorException
import pl.borowa5b.car.rental.shared.domain.exception.validation.ThrowingValidationExceptionHandler
import pl.borowa5b.car.rental.shared.domain.exception.validation.ValidationError
import pl.borowa5b.car.rental.shared.domain.exception.validation.ValidationExceptionHandler

data class Page(val number: Int, val size: Int, val order: Sort.Order) {

    init {
        validate(number, size)
    }

    companion object {

        fun validate(
            number: Int?,
            size: Int?,
            validationExceptionHandler: ValidationExceptionHandler = ThrowingValidationExceptionHandler()
        ) {
            number?.let {
                if (it < 1) {
                    validationExceptionHandler.handle(
                        ValidationErrorException(
                            ValidationError(
                                "Invalid page number",
                                "Page number must be greater than 0",
                                "number"
                            )
                        )
                    )
                }
            }

            size?.let {
                if (it < 1) {
                    validationExceptionHandler.handle(
                        ValidationErrorException(
                            ValidationError(
                                "Invalid page size",
                                "Page size must be greater than 0",
                                "size"
                            )
                        )
                    )
                }
            }
        }
    }

    fun getOrderAsString(): String = "${order.property.toSnakeCase()} ${order.direction.name}"

    private fun String.toSnakeCase() = replace(Regex("(?<=.)(?=\\p{Upper})"), "_").uppercase()
}