package pl.borowa5b.car.rental.rentals.domain.event

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.rentals.domain.model.DomainObjects.rental

class RentalMadeEventTest {

    @Test
    fun `should create from rental`() {
        // given
        val rental = rental()

        // when
        val result = RentalMadeEvent(rental)

        // then
        assertThat(result.rentalId).isEqualTo(rental.id.value)
        assertThat(result.carId).isEqualTo(rental.carId.value)
        assertThat(result.customerId).isEqualTo(rental.customerId.value)
        assertThat(result.startDate).isEqualTo(rental.startDate)
        assertThat(result.endDate).isEqualTo(rental.endDate)
        assertThat(result.price).isEqualTo(rental.price)
        assertThat(result.getType()).isEqualTo("RentalMade")
        assertThat(result.getVersion()).isEqualTo("1.0")
    }
}