package pl.borowa5b.car.rental.customers.application.endpoint

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.test.context.support.WithMockUser
import pl.borowa5b.car.rental.customers.application.filter.FilterObjects.getCustomersFilter
import pl.borowa5b.car.rental.customers.infrastructure.entity.EntityObjects.customerEntity
import pl.borowa5b.car.rental.shared.application.request.RequestObjects.pageRequest
import pl.borowa5b.car.rental.shared.domain.vo.Role
import pl.borowa5b.car.rental.shared.helper.IntegrationTest
import pl.borowa5b.car.rental.shared.helper.TestSpringCustomerRepository

@IntegrationTest
class GetCustomersEndpointIT {

    @Autowired
    private lateinit var getCustomersEndpoint: GetCustomersEndpoint

    @Autowired
    private lateinit var customerRepository: TestSpringCustomerRepository

    @Test
    @WithMockUser(roles = [Role.ADMIN])
    fun `should get cars`() {
        // given
        val customerEntity1 = customerEntity(email = "email1@google.com")
        val customerEntity2 = customerEntity(email = "email2@google.com")
        customerRepository.save(customerEntity1)
        customerRepository.save(customerEntity2)
        val filter = getCustomersFilter()
        val pageRequest = pageRequest()

        // when
        val result = getCustomersEndpoint.getCustomers(filter, pageRequest)

        // then
        val data = result.data
        assertThat(data).hasSize(2)
        assertThat(data[0].id).isEqualTo(customerEntity2.id)
        assertThat(data[1].id).isEqualTo(customerEntity1.id)
    }

    @Test
    @WithMockUser(roles = [Role.CARS])
    fun `should throw access denied exception when invoked with wrong role`() {
        // given
        val filter = getCustomersFilter()
        val pageRequest = pageRequest()

        // when
        val result = catchThrowable { getCustomersEndpoint.getCustomers(filter, pageRequest) }

        // then
        assertThat(result)
            .isInstanceOf(AccessDeniedException::class.java)
            .hasMessage("Access Denied")
    }
}
