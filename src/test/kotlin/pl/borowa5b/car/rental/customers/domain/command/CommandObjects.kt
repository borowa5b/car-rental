package pl.borowa5b.car.rental.customers.domain.command

import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.customers.domain.shared.vo.ValueObjects.customerId

object CommandObjects {

    fun saveCustomerCommand(
        id: CustomerId = customerId(),
        name: String = "Joe",
        surname: String = "Doe",
        email: String = "joe.doe@email.com",
        phone: String = "952123531",
        address: String = "Kwiatkowa 9, 00-000 Warszawa",
        documentNumber: String = "FAE124532"
    ): SaveCustomerCommand = SaveCustomerCommand(
        id = id.value,
        name = name,
        surname = surname,
        email = email,
        phone = phone,
        address = address,
        documentNumber = documentNumber
    )
}