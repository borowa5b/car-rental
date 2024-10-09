package pl.borowa5b.car.rental.cars.application.response

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.cars.domain.repository.read.ReadObjects.carDetails

class CarResponseTest {

    @Test
    fun `should create response from car details`() {
        // given
        val carDetails = carDetails()

        // when
        val result = CarResponse.fromDetails(carDetails)

        // then
        assertThat(result.id).isEqualTo(carDetails.id)
        assertThat(result.brand).isEqualTo(carDetails.brand)
        assertThat(result.model).isEqualTo(carDetails.model)
        assertThat(result.generation).isEqualTo(carDetails.generation)
        assertThat(result.productionYear).isEqualTo(carDetails.productionYear)
        assertThat(result.color).isEqualTo(carDetails.color)
        assertThat(result.pricePerDay.intValueExact()).isEqualTo(carDetails.pricePerDay.intValueExact())
        assertThat(result.quantity).isEqualTo(carDetails.quantity)
        assertThat(result.creationDate).isEqualTo(carDetails.creationDate)
    }
}