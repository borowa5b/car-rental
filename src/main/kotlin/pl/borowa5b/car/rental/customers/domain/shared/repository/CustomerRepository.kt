package pl.borowa5b.car.rental.customers.domain.shared.repository

import pl.borowa5b.car.rental.customers.domain.model.Customer
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId

interface CustomerRepository {

    fun exists(customerId: CustomerId): Boolean

    fun save(customer: Customer): Customer

    fun findById(id: CustomerId): Customer?
}