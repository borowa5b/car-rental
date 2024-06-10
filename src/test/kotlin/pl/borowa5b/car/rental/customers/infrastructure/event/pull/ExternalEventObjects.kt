package pl.borowa5b.car.rental.customers.infrastructure.event.pull

import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.customers.domain.shared.vo.ValueObjects.customerId

object ExternalEventObjects {

    fun customerCreatedEvent(
        id: CustomerId = customerId(),
        name: String = "Jan",
        surname: String = "Kowalski",
        email: String = "email",
        phone: String = "phone",
        address: String = "address",
        documentNumber: String = "documentNumber"
    ): CustomerCreatedEvent = CustomerCreatedEvent(
        id = id.value,
        name = name,
        surname = surname,
        email = email,
        phone = phone,
        address = address,
        documentNumber = documentNumber
    )
}