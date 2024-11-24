package pl.borowa5b.car.rental.customers.domain.repository.read

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.customers.domain.repository.read.ReadObjects.customerDetails
import pl.borowa5b.car.rental.customers.domain.repository.read.ReadObjects.customerQuery
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.customers.domain.shared.vo.ValueObjects.customerId
import pl.borowa5b.car.rental.shared.domain.pagination.Page
import pl.borowa5b.car.rental.shared.domain.pagination.Pageable
import pl.borowa5b.car.rental.shared.domain.pagination.PaginationObjects.page

class CustomerFinderTest {

    private val customerFinder: CustomerFinder = TestCustomerFinder()

    @Test
    fun `should find customers by query`() {
        // given
        val customerDetails1 = customerDetails(name = "John", surname = "Doe", email = "john1@example.com")
        val customerDetails2 = customerDetails(name = "Jane", surname = "Doe", email = "jane2@example.com")
        val customerDetails3 = customerDetails(name = "Jane", surname = "Doe", email = "jane3@example.com")
        (customerFinder as TestCustomerFinder).save(customerDetails1, customerDetails2, customerDetails3)
        val query = customerQuery(name = "Jane")

        // when
        val result = customerFinder.findBy(query, page())

        // then
        val data = result.data
        assertThat(data).hasSize(2)
        assertThat(data[0]).isEqualTo(customerDetails2)
        assertThat(data[1]).isEqualTo(customerDetails3)
    }

    @Test
    fun `should find customer by id`() {
        // given
        val customerId = customerId()
        val customerDetails = customerDetails(id = customerId)
        (customerFinder as TestCustomerFinder).save(customerDetails)

        // when
        val result = customerFinder.findBy(customerId)

        // then
        assertThat(result).isEqualTo(customerDetails)
    }

    private class TestCustomerFinder(private val customers: ArrayList<CustomerDetails> = ArrayList()) : CustomerFinder {

        override fun findBy(query: CustomerQuery, page: Page): Pageable<CustomerDetails> =
            Pageable.of(customers.filter { customer ->
                var condition = true
                query.id?.let {
                    condition = condition && it.value == customer.id
                }
                query.name?.let {
                    condition = condition && it == customer.name
                }
                query.surname?.let {
                    condition = condition && it == customer.surname
                }
                query.email?.let {
                    condition = condition && it == customer.email
                }
                query.phoneNumber?.let {
                    condition = condition && it == customer.phoneNumber
                }
                condition
            }.toList(), page)

        override fun findBy(customerId: CustomerId): CustomerDetails = customers.first { it.id == customerId.value }

        fun save(vararg customer: CustomerDetails) = customer.forEach { customers.add(it) }
    }
}
