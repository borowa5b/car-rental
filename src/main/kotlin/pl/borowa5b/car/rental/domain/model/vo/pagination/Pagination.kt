package pl.borowa5b.car.rental.domain.model.vo.pagination

import pl.borowa5b.car.rental.domain.exception.ValidationErrorException
import pl.borowa5b.car.rental.domain.exception.validation.ThrowingValidationExceptionHandler
import pl.borowa5b.car.rental.domain.exception.validation.ValidationError
import pl.borowa5b.car.rental.domain.exception.validation.ValidationExceptionHandler

data class Pagination(val number: Int, val size: Int, val hasNext: Boolean, val hasPrevious: Boolean) {

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
                                "Page number must be greater than 0"
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
                                "Page size must be greater than 0"
                            )
                        )
                    )
                }
            }
        }

        fun of(data: List<*>, page: Page) = Pagination(
            page.number,
            page.size,
            data.size > page.size,
            page.number > 1
        )
    }
}