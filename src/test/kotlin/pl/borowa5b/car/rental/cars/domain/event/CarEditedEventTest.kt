package pl.borowa5b.car.rental.cars.domain.event

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.cars.domain.shared.model.DomainObjects.car

class CarEditedEventTest {

    @Test
    fun `should create from car`() {
        // given
        val car = car()

        // when
        val result = CarEditedEvent(car)

        // then
        assertThat(result.id).isEqualTo(car.id.value)
        assertThat(result.brand).isEqualTo(car.brand.name)
        assertThat(result.model).isEqualTo(car.model)
        assertThat(result.generation).isEqualTo(car.generation)
        assertThat(result.productionYear).isEqualTo(car.productionYear)
        assertThat(result.color).isEqualTo(car.color)
        assertThat(result.pricePerDay).isEqualTo(car.pricePerDay)
        assertThat(result.quantity).isEqualTo(car.quantity)
        assertThat(result.getType()).isEqualTo("CarEdited")
        assertThat(result.getVersion()).isEqualTo("1.0")
    }
}