package pl.borowa5b.car.rental.customers.application.response

import pl.borowa5b.car.rental.customers.domain.repository.read.CustomerDetails
import java.time.OffsetDateTime

data class CustomerResponse(
    val id: String,
    val name: String,
    val surname: String,
    val email: String,
    val phoneNumber: String,
    val address: String,
    val documentNumber: String,
    val creationDate: OffsetDateTime
) {

    companion object {
        fun fromDetails(customerDetails: CustomerDetails): CustomerResponse = CustomerResponse(
            id = customerDetails.id,
            name = customerDetails.name,
            surname = customerDetails.surname,
            email = customerDetails.email,
            phoneNumber = customerDetails.phoneNumber,
            address = customerDetails.address,
            documentNumber = customerDetails.documentNumber,
            creationDate = customerDetails.creationDate
        )
    }
}
