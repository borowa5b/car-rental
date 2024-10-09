package pl.borowa5b.car.rental.cars.application.endpoint

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser
import pl.borowa5b.car.rental.cars.application.filter.FilterObjects.getCarsFilter
import pl.borowa5b.car.rental.cars.infrastructure.entity.EntityObjects.carEntity
import pl.borowa5b.car.rental.shared.application.request.RequestObjects.pageRequest
import pl.borowa5b.car.rental.shared.domain.vo.Role
import pl.borowa5b.car.rental.shared.helper.Database
import pl.borowa5b.car.rental.shared.helper.IntegrationTest
import pl.borowa5b.car.rental.shared.helper.TestSpringCarRepository

@IntegrationTest
class GetCarsEndpointIT {

    @Autowired
    private lateinit var database: Database

    @Autowired
    private lateinit var getCarsEndpoint: GetCarsEndpoint

    @Autowired
    private lateinit var carRepository: TestSpringCarRepository

    @BeforeEach
    fun `before each`() {
        database.prepare()
    }

    @Test
    @WithMockUser(roles = [Role.USER])
    fun `should get cars`() {
        // given
        val carEntity1 = carEntity()
        val carEntity2 = carEntity()
        carRepository.save(carEntity1)
        carRepository.save(carEntity2)
        val filter = getCarsFilter()
        val pageRequest = pageRequest()

        // when
        val result = getCarsEndpoint.getCars(filter, pageRequest)

        // then
        val data = result.data
        assertThat(data).hasSize(2)
        assertThat(data[0].id).isEqualTo(carEntity2.id)
        assertThat(data[1].id).isEqualTo(carEntity1.id)
    }
}