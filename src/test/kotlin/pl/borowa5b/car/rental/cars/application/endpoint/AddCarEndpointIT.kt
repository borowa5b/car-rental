package pl.borowa5b.car.rental.cars.application.endpoint

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser
import pl.borowa5b.car.rental.cars.application.request.RequestObjects.addCarRequest
import pl.borowa5b.car.rental.shared.domain.vo.Role
import pl.borowa5b.car.rental.shared.helper.ApplicationEventAssertions.assertApplicationEvents
import pl.borowa5b.car.rental.shared.helper.Database
import pl.borowa5b.car.rental.shared.helper.IntegrationTest
import pl.borowa5b.car.rental.shared.helper.TestSpringCarRepository

@IntegrationTest
class AddCarEndpointIT {

    @Autowired
    private lateinit var database: Database

    @Autowired
    private lateinit var addCarEndpoint: AddCarEndpoint

    @Autowired
    private lateinit var carRepository: TestSpringCarRepository

    @BeforeEach
    fun `before each`() {
        database.prepare()
    }

    @Test
    @WithMockUser(roles = [Role.ADMIN])
    fun `should add car`() {
        // given
        val request = addCarRequest()

        // when
        val result = addCarEndpoint.add(request)

        // then
        val addedCar = carRepository.findById(result.body!!.carId).orElse(null)
        assertThat(addedCar.brand.name).isEqualTo(request.brand)
        assertThat(addedCar.model).isEqualTo(request.model!!.lowercase())
        assertThat(addedCar.generation).isEqualTo(request.generation!!.lowercase())
        assertThat(addedCar.year).isEqualTo(request.year)
        assertThat(addedCar.color).isEqualTo(request.color!!.lowercase())
        assertThat(addedCar.pricePerDay.intValueExact()).isEqualTo(request.pricePerDay!!.intValueExact())
        assertThat(addedCar.quantity).isEqualTo(request.quantity)
        assertThat(addedCar.entityVersion).isEqualTo(0L)

        assertApplicationEvents("CarAdded")
    }
}