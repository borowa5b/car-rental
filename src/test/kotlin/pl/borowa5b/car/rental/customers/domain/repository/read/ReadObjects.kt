package pl.borowa5b.car.rental.customers.domain.repository.read

import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.customers.domain.shared.vo.ValueObjects.customerId
import java.time.OffsetDateTime

object ReadObjects {

    fun customerDetails(
        id: CustomerId = customerId(),
        name: String = "John",
        surname: String = "Doe",
        email: String = "john.doe@example.com",
        phoneNumber: String = "+48123123123",
        address: String = "Warsaw",
        documentNumber: String = "ABC123123123"
    ): CustomerDetails = CustomerDetails(
        id = id.value,
        name = name,
        surname = surname,
        email = email,
        phoneNumber = phoneNumber,
        address = address,
        documentNumber = documentNumber,
        creationDate = OffsetDateTime.parse("2022-01-01T12:00:00Z"),
        modificationDate = OffsetDateTime.parse("2022-01-01T12:00:00Z"),
        entityVersion = 0
    )

    fun customerQuery(
        id: CustomerId? = null,
        name: String? = null,
        surname: String? = null,
        email: String? = null,
        phoneNumber: String? = null
    ): CustomerQuery = CustomerQuery(
        id = id,
        name = name,
        surname = surname,
        email = email,
        phoneNumber = phoneNumber
    )
}