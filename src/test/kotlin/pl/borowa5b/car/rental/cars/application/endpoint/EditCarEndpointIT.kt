package pl.borowa5b.car.rental.cars.application.endpoint

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.test.context.support.WithMockUser
import pl.borowa5b.car.rental.cars.application.request.RequestObjects.editCarRequest
import pl.borowa5b.car.rental.cars.domain.vo.Brand
import pl.borowa5b.car.rental.cars.infrastructure.entity.EntityObjects.carEntity
import pl.borowa5b.car.rental.shared.domain.vo.Role
import pl.borowa5b.car.rental.shared.helper.Database
import pl.borowa5b.car.rental.shared.helper.IntegrationTest
import pl.borowa5b.car.rental.shared.helper.TestSpringCarRepository
import java.math.BigDecimal

@IntegrationTest
class EditCarEndpointIT {

    @Autowired
    private lateinit var database: Database

    @Autowired
    private lateinit var editCarEndpoint: EditCarEndpoint

    @Autowired
    private lateinit var carRepository: TestSpringCarRepository

    @BeforeEach
    fun `before each`() {
        database.prepare()
    }

    @Test
    @WithMockUser(roles = [Role.ADMIN])
    fun `should edit car`() {
        // given
        val carEntity = carRepository.save(carEntity())
        val request = editCarRequest(
            carId = carEntity.id,
            brand = Brand.VOLVO.name,
            model = "new model",
            generation = "new generation",
            productionYear = 2021,
            color = "new color",
            pricePerDay = BigDecimal("123"),
            quantity = 123
        )

        // when
        val result = editCarEndpoint.edit(request)

        // then
        assertThat(result.statusCode.value()).isEqualTo(HttpStatus.OK.value())

        val editedCarOptional = carRepository.findById(carEntity.id)
        assertThat(editedCarOptional.isPresent).isTrue()

        val editedCar = editedCarOptional.get()
        assertThat(editedCar.brand.name).isEqualTo(request.brand)
        assertThat(editedCar.model).isEqualTo(request.model)
        assertThat(editedCar.generation).isEqualTo(request.generation)
        assertThat(editedCar.productionYear).isEqualTo(request.productionYear)
        assertThat(editedCar.color).isEqualTo(request.color)
        assertThat(editedCar.pricePerDay.intValueExact()).isEqualTo(request.pricePerDay!!.intValueExact())
        assertThat(editedCar.quantity).isEqualTo(request.quantity)
        assertThat(editedCar.entityVersion).isEqualTo(1L)
    }
}