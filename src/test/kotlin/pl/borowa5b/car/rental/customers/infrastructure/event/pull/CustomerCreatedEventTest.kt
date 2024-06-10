package pl.borowa5b.car.rental.customers.infrastructure.event.pull

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.customers.infrastructure.event.pull.ExternalEventObjects.customerCreatedEvent

class CustomerCreatedEventTest {

    @Test
    fun `should convert to command`() {
        // given
        val event = customerCreatedEvent()

        // when
        val result = event.toCommand()

        // then
        assertThat(result.id).isEqualTo(event.id)
        assertThat(result.name).isEqualTo(event.name)
        assertThat(result.surname).isEqualTo(event.surname)
        assertThat(result.email).isEqualTo(event.email)
        assertThat(result.phone).isEqualTo(event.phone)
        assertThat(result.address).isEqualTo(event.address)
        assertThat(result.documentNumber).isEqualTo(event.documentNumber)
    }
}