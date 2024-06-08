package pl.borowa5b.car.rental.domain.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import pl.borowa5b.car.rental.domain.model.Customer
import pl.borowa5b.car.rental.domain.model.DomainObjects.customer
import pl.borowa5b.car.rental.domain.model.vo.CustomerId
import pl.borowa5b.car.rental.infrastructure.entity.CustomerEntity

class CustomerRepositoryTest {

    private val customerRepository: CustomerRepository = TestCustomerRepository()

    @BeforeEach
    fun `before each`() {
        (customerRepository as TestCustomerRepository).deleteAll()
    }

    @ParameterizedTest
    @CsvSource(
        "CTR1, true",
        "CTR2, false"
    )
    fun `should check if customer exists`(customerId: String, expectedResult: Boolean) {
        // given
        val customer = customer(id = CustomerId("CTR1"))
        customerRepository.save(customer)

        // when
        val result = customerRepository.exists(CustomerId(customerId))

        // then
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `should save customer`() {
        // given
        val customer = customer()

        // when
        val result = customerRepository.save(customer)

        // then
        assertThat(result.id).isEqualTo(customer.id.value)
    }

    private class TestCustomerRepository(private val customers: ArrayList<CustomerEntity> = ArrayList()) :
        CustomerRepository {

        override fun exists(customerId: CustomerId): Boolean = customers.any { it.id == customerId.value }

        override fun save(customer: Customer): CustomerEntity {
            val entity = CustomerEntity.fromDomain(customer)
            customers.add(entity)
            return entity
        }

        override fun findById(id: CustomerId): Customer? = customers.find { it.id == id.value }?.toDomain()

        fun deleteAll() = customers.clear()
    }
}