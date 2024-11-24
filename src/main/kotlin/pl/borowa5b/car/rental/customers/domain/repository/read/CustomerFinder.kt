package pl.borowa5b.car.rental.customers.domain.repository.read

import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.shared.domain.pagination.Page
import pl.borowa5b.car.rental.shared.domain.pagination.Pageable

interface CustomerFinder {

    fun findBy(query: CustomerQuery, page: Page): Pageable<CustomerDetails>

    fun findBy(customerId: CustomerId): CustomerDetails
}