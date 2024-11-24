package pl.borowa5b.car.rental.customers.application.response

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.customers.domain.repository.read.ReadObjects.customerDetails

class CustomerResponseTest {

    @Test
    fun `should create response from customer details`() {
        // given
        val customerDetails = customerDetails()

        // when
        val result = CustomerResponse.fromDetails(customerDetails)

        // then
        assertThat(result.id).isEqualTo(customerDetails.id)
        assertThat(result.name).isEqualTo(customerDetails.name)
        assertThat(result.surname).isEqualTo(customerDetails.surname)
        assertThat(result.email).isEqualTo(customerDetails.email)
        assertThat(result.phoneNumber).isEqualTo(customerDetails.phoneNumber)
        assertThat(result.address).isEqualTo(customerDetails.address)
        assertThat(result.documentNumber).isEqualTo(customerDetails.documentNumber)
        assertThat(result.creationDate).isEqualTo(customerDetails.creationDate)

    }
}