package pl.borowa5b.car.rental.domain.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import pl.borowa5b.car.rental.domain.model.Customer
import pl.borowa5b.car.rental.domain.model.CustomerId
import pl.borowa5b.car.rental.domain.model.DomainObjects.customer
import pl.borowa5b.car.rental.infrastructure.entity.CustomerEntity

class CustomerRepositoryTest {

    @ParameterizedTest
    @CsvSource(
        "CTR1, true",
        "CTR2, false"
    )
    fun `should check if customer exists`(customerId: String, expectedResult: Boolean) {
        // given
        val customerRepository = TestCustomerRepository()
        val customer = customer(id = CustomerId("CTR1"))
        customerRepository.save(customer)

        // when
        val result = customerRepository.exists(CustomerId(customerId))

        // then
        assertThat(result).isEqualTo(expectedResult)
    }

    private class TestCustomerRepository(private val cars: ArrayList<CustomerEntity> = ArrayList()) :
        CustomerRepository {

        override fun exists(customerId: CustomerId): Boolean = cars.any { it.id == customerId.value }

        fun save(customer: Customer) = cars.add(CustomerEntity.fromDomain(customer))
    }
}