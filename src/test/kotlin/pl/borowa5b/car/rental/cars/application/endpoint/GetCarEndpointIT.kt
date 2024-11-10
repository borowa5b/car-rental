package pl.borowa5b.car.rental.cars.application.endpoint

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser
import pl.borowa5b.car.rental.cars.domain.shared.exception.CarNotFoundException
import pl.borowa5b.car.rental.cars.domain.shared.vo.ValueObjects.carId
import pl.borowa5b.car.rental.cars.infrastructure.entity.EntityObjects.carEntity
import pl.borowa5b.car.rental.cars.infrastructure.repository.SpringJpaCarRepository
import pl.borowa5b.car.rental.shared.domain.vo.Role
import pl.borowa5b.car.rental.shared.helper.IntegrationTest
import java.time.temporal.ChronoUnit

@IntegrationTest
class GetCarEndpointIT {

    @Autowired
    private lateinit var carRepository: SpringJpaCarRepository

    @Autowired
    private lateinit var endpoint: GetCarEndpoint

    @Test
    @WithMockUser(roles = [Role.USER])
    fun `should get rental`() {
        // given
        val carEntity = carEntity()
        carRepository.save(carEntity)

        // when
        val result = endpoint.getCar(carEntity.id)

        // then
        assertThat(result.id).isEqualTo(carEntity.id)
        assertThat(result.brand).isEqualTo(carEntity.brand.name)
        assertThat(result.model).isEqualTo(carEntity.model)
        assertThat(result.generation).isEqualTo(carEntity.generation)
        assertThat(result.productionYear).isEqualTo(carEntity.productionYear)
        assertThat(result.color).isEqualTo(carEntity.color)
        assertThat(result.pricePerDay.intValueExact()).isEqualTo(carEntity.pricePerDay.intValueExact())
        assertThat(result.quantity).isEqualTo(carEntity.quantity)
        assertThat(result.creationDate).isEqualTo(carEntity.creationDate.truncatedTo(ChronoUnit.MICROS))
    }

    @Test
    @WithMockUser(roles = [Role.USER])
    fun `should throw exception when rental not found`() {
        // given
        val carId = carId()

        // when
        val result = catchThrowable { endpoint.getCar(carId.value) }

        // then
        assertThat(result).isInstanceOf(CarNotFoundException::class.java)
        assertThat(result.message).isEqualTo("Car with id ${carId.value} not found")
    }
}