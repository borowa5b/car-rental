package pl.borowa5b.car.rental.customers.domain

import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.customers.domain.command.SaveCustomerCommand
import pl.borowa5b.car.rental.customers.domain.model.Customer
import pl.borowa5b.car.rental.customers.domain.shared.repository.CustomerRepository
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId

@Component
class CustomerSaver(private val customerRepository: CustomerRepository) {

    fun save(command: SaveCustomerCommand) {
        val customer = Customer(
            id = CustomerId(command.id),
            name = command.name,
            surname = command.surname,
            email = command.email,
            phoneNumber = command.phone,
            address = command.address,
            documentNumber = command.documentNumber
        )
        customerRepository.save(customer)
    }
}