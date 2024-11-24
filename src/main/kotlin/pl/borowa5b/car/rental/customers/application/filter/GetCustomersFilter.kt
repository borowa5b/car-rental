package pl.borowa5b.car.rental.customers.application.filter

import io.swagger.v3.oas.annotations.Parameter
import org.springdoc.core.annotations.ParameterObject
import pl.borowa5b.car.rental.customers.domain.repository.read.CustomerQuery
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.shared.domain.exception.validation.ValidationExceptionHandler
import pl.borowa5b.car.rental.shared.domain.exception.validation.Validator

@ParameterObject
data class GetCustomersFilter(
    @Parameter(description = "Customer identifier", example = "CTR1214242443242")
    val id: String? = null,

    @Parameter(description = "Customer name", example = "John Doe")
    val name: String? = null,

    @Parameter(description = "Customer surname", example = "Smith")
    val surname: String? = null,

    @Parameter(description = "Customer email", example = "john.doe@example.com")
    val email: String? = null,

    @Parameter(description = "Customer phone number", example = "+48123123123")
    val phoneNumber: String? = null
) {

    fun validate(validationExceptionHandler: ValidationExceptionHandler) {
        id?.let {
            CustomerId.validate(it, "id", validationExceptionHandler)
        }
        name?.let {
            Validator.isNotBlank(it, "name", validationExceptionHandler)
        }
        surname?.let {
            Validator.isNotBlank(it, "surname", validationExceptionHandler)
        }
        email?.let {
            Validator.isNotBlank(it, "email", validationExceptionHandler)
        }
    }

    fun toQuery(): CustomerQuery = CustomerQuery(
        id = id?.let { CustomerId(it) },
        name = name,
        surname = surname,
        email = email,
        phoneNumber = phoneNumber
    )
}