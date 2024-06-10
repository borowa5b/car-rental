package pl.borowa5b.car.rental.shared.application.request

import io.swagger.v3.oas.annotations.Parameter
import org.springdoc.core.annotations.ParameterObject
import pl.borowa5b.car.rental.shared.domain.exception.validation.ValidationExceptionHandler
import pl.borowa5b.car.rental.shared.domain.exception.validation.Validator
import pl.borowa5b.car.rental.shared.domain.pagination.OrderParser
import pl.borowa5b.car.rental.shared.domain.pagination.Page

@ParameterObject
data class PageRequest(
    @Parameter(description = "Page number (default: 1", example = "1")
    val number: Int? = 1,

    @Parameter(description = "Page size (default: 100)", example = "100")
    val size: Int? = 100,

    @Parameter(example = "creationDate,DESC", description = "Page order (default: creationDate,DESC)")
    val order: String? = "creationDate,DESC"
) {

    fun validate(validationExceptionHandler: ValidationExceptionHandler) {
        Validator.isNotNull(number, "number", validationExceptionHandler)
        Validator.isNotNull(size, "size", validationExceptionHandler)
        Validator.isNotNullOrBlank(order, "order", validationExceptionHandler)

        Page.validate(number, size, validationExceptionHandler)
        OrderParser.validate(order, "order", validationExceptionHandler)
    }

    fun toPage(): Page = Page(number!!, size!!, OrderParser.parse(order!!))
}