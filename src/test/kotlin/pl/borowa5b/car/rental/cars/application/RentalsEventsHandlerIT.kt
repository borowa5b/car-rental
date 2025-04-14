package pl.borowa5b.car.rental.cars.application

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import pl.borowa5b.car.rental.cars.domain.shared.vo.ValueObjects.carId
import pl.borowa5b.car.rental.cars.infrastructure.event.pull.ExternalEventObjects.rentalMadeEvent
import pl.borowa5b.car.rental.shared.helper.IntegrationTest
import pl.borowa5b.car.rental.shared.helper.IntegrationTestEntityCreator.createCarEntity
import pl.borowa5b.car.rental.shared.helper.TestSpringCarRepository

@IntegrationTest
class RentalsEventsHandlerIT {

    @Autowired
    private lateinit var carRepository: TestSpringCarRepository

    @Autowired
    private lateinit var handler: RentalsEventsHandler

    @Test
    fun `should handle rental made event`() {
        // given
        val carId = carId()
        val quantityBefore = 10
        createCarEntity(id = carId.value, quantity = quantityBefore)
        val event = rentalMadeEvent(carId = carId)

        // when
        handler.handle(event)

        // then
        val result = carRepository.findById(carId.value).orElse(null)
        assertThat(result).isNotNull()
        assertThat(result.quantity).isEqualTo(quantityBefore - 1)
    }
}