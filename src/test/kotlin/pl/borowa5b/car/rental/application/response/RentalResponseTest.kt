package pl.borowa5b.car.rental.application.response

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.domain.model.DomainObjects.rentalDetails

class RentalResponseTest {

    @Test
    fun `should create response from rental details`() {
        // given
        val rentalDetails = rentalDetails()

        // when
        val result = RentalResponse.fromDetails(rentalDetails)

        // then
        assertThat(result.id).isEqualTo(rentalDetails.id)
        assertThat(result.carId).isEqualTo(rentalDetails.carId)
        assertThat(result.customerId).isEqualTo(rentalDetails.customerId)
        assertThat(result.startDate).isEqualTo(rentalDetails.startDate)
        assertThat(result.endDate).isEqualTo(rentalDetails.endDate)
        assertThat(result.price).isEqualTo(rentalDetails.price)
        assertThat(result.status).isEqualTo(rentalDetails.status)
        assertThat(result.creationDate).isEqualTo(rentalDetails.creationDate)
    }
}