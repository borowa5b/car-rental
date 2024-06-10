package pl.borowa5b.car.rental.customers.domain.shared.model

import pl.borowa5b.car.rental.customers.domain.model.Customer
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.customers.domain.shared.vo.ValueObjects.customerId

object DomainObjects {

    fun customer(
        id: CustomerId = customerId(),
        name: String = "John",
        surname: String = "Doe",
        email: String = "john.doe@example.com",
        phoneNumber: String = "+48123123123",
        address: String = "Warsaw",
        documentNumber: String = "ABC123123123"
    ): Customer = Customer(
        id = id,
        name = name,
        surname = surname,
        email = email,
        phoneNumber = phoneNumber,
        address = address,
        documentNumber = documentNumber,
        version = 0
    )
}