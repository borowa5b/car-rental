package pl.borowa5b.car.rental.customers.infrastructure.entity

import pl.borowa5b.car.rental.customers.domain.shared.vo.ValueObjects.customerId

object EntityObjects {

    fun customerEntity(
        id: String = customerId().value,
        name: String = "John",
        surname: String = "Doe",
        email: String = "john.doe@example.com",
        phoneNumber: String = "+48123123123",
        address: String = "Warsaw",
        documentNumber: String = "ABC123123123"
    ): CustomerEntity = CustomerEntity(
        id = id,
        name = name,
        surname = surname,
        email = email,
        phoneNumber = phoneNumber,
        address = address,
        documentNumber = documentNumber,
        entityVersion = 0
    )
}