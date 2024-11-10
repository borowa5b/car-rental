package pl.borowa5b.car.rental.customers.infrastructure

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import pl.borowa5b.car.rental.customers.domain.shared.repository.CustomerRepository
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.customers.infrastructure.event.pull.ExternalEventObjects.customerCreatedEvent
import pl.borowa5b.car.rental.shared.helper.IntegrationTest

@IntegrationTest
class CustomerEventsHandlerIT {

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var handler: CustomerEventsHandler

    @Test
    fun `should handle customer created event`() {
        // given
        val event = customerCreatedEvent()

        // when
        handler.handle(event)

        // then
        val savedCustomer = customerRepository.findById(CustomerId(event.id))!!
        assertThat(savedCustomer.id.value).isEqualTo(event.id)
        assertThat(savedCustomer.name).isEqualTo(event.name)
        assertThat(savedCustomer.surname).isEqualTo(event.surname)
        assertThat(savedCustomer.email).isEqualTo(event.email)
        assertThat(savedCustomer.phoneNumber).isEqualTo(event.phone)
        assertThat(savedCustomer.address).isEqualTo(event.address)
        assertThat(savedCustomer.documentNumber).isEqualTo(event.documentNumber)
    }
}