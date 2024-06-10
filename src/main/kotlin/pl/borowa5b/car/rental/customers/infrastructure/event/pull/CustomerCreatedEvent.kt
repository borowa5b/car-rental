package pl.borowa5b.car.rental.customers.infrastructure.event.pull

import pl.borowa5b.car.rental.customers.domain.command.SaveCustomerCommand

data class CustomerCreatedEvent(
    val id: String,
    val name: String,
    val surname: String,
    val email: String,
    val phone: String,
    val address: String,
    val documentNumber: String
) {
    fun toCommand(): SaveCustomerCommand = SaveCustomerCommand(
        id = id,
        name = name,
        surname = surname,
        email = email,
        phone = phone,
        address = address,
        documentNumber = documentNumber,
    )
}