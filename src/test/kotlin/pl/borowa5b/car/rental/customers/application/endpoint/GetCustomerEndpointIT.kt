package pl.borowa5b.car.rental.customers.application.endpoint

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser
import pl.borowa5b.car.rental.customers.domain.shared.exception.CustomerNotFoundException
import pl.borowa5b.car.rental.customers.domain.shared.vo.ValueObjects.customerId
import pl.borowa5b.car.rental.customers.infrastructure.entity.EntityObjects.customerEntity
import pl.borowa5b.car.rental.customers.infrastructure.repository.SpringJpaCustomerRepository
import pl.borowa5b.car.rental.shared.domain.vo.Role
import pl.borowa5b.car.rental.shared.helper.IntegrationTest
import java.time.temporal.ChronoUnit

@IntegrationTest
class GetCustomerEndpointIT {

    @Autowired
    private lateinit var customerRepository: SpringJpaCustomerRepository

    @Autowired
    private lateinit var endpoint: GetCustomerEndpoint

    @Test
    @WithMockUser(roles = [Role.ADMIN])
    fun `should get customer`() {
        // given
        val customerEntity = customerEntity()
        customerRepository.save(customerEntity)

        // when
        val result = endpoint.getCustomer(customerEntity.id)

        // then
        assertThat(result.id).isEqualTo(customerEntity.id)
        assertThat(result.name).isEqualTo(customerEntity.name)
        assertThat(result.surname).isEqualTo(customerEntity.surname)
        assertThat(result.email).isEqualTo(customerEntity.email)
        assertThat(result.phoneNumber).isEqualTo(customerEntity.phoneNumber)
        assertThat(result.address).isEqualTo(customerEntity.address)
        assertThat(result.documentNumber).isEqualTo(customerEntity.documentNumber)
        assertThat(result.creationDate).isEqualTo(customerEntity.creationDate.truncatedTo(ChronoUnit.MICROS))
    }

    @Test
    @WithMockUser(roles = [Role.ADMIN])
    fun `should throw exception when customer not found`() {
        // given
        val customerId = customerId()

        // when
        val result = catchThrowable { endpoint.getCustomer(customerId.value) }

        // then
        assertThat(result).isInstanceOf(CustomerNotFoundException::class.java)
        assertThat(result.message).isEqualTo("Customer with id ${customerId.value} not found")
    }
}