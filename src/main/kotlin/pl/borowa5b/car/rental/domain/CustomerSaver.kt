package pl.borowa5b.car.rental.domain

import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.domain.command.SaveCustomerCommand
import pl.borowa5b.car.rental.domain.model.Customer
import pl.borowa5b.car.rental.domain.model.vo.CustomerId
import pl.borowa5b.car.rental.domain.repository.CustomerRepository

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