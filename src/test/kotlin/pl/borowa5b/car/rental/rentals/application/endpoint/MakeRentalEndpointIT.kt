package pl.borowa5b.car.rental.rentals.application.endpoint

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser
import pl.borowa5b.car.rental.rentals.application.request.RequestObjects.makeRentalRequest
import pl.borowa5b.car.rental.rentals.domain.vo.RentalStatus
import pl.borowa5b.car.rental.shared.domain.vo.Role
import pl.borowa5b.car.rental.shared.helper.IntegrationTest
import pl.borowa5b.car.rental.shared.helper.IntegrationTestAssertions.assertApplicationEvents
import pl.borowa5b.car.rental.shared.helper.IntegrationTestAssertions.assertCarQuantity
import pl.borowa5b.car.rental.shared.helper.IntegrationTestEntityCreator.createCarEntity
import pl.borowa5b.car.rental.shared.helper.IntegrationTestEntityCreator.createCustomerEntity
import pl.borowa5b.car.rental.shared.helper.TestSpringRentalRepository
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

@IntegrationTest
class MakeRentalEndpointIT {

    @Autowired
    private lateinit var endpoint: MakeRentalEndpoint

    @Autowired
    private lateinit var rentalRepository: TestSpringRentalRepository

    @Test
    @WithMockUser(roles = [Role.RENTALS])
    fun `should make rental`() {
        // given
        val request = makeRentalRequest()
        val carQuantityBefore = 10
        createCarEntity(id = request.carId!!, quantity = carQuantityBefore)
        createCustomerEntity(request.customerId!!)

        // when
        val result = endpoint.make(request)

        // then
        val rental = rentalRepository.findById(result.body!!.rentalId).orElse(null)
        assertThat(rental.carId).isEqualTo(request.carId)
        assertThat(rental.customerId).isEqualTo(request.customerId)
        assertThat(rental.startDate).isEqualTo(
            OffsetDateTime.parse(request.startDate!!).truncatedTo(ChronoUnit.MICROS)
        )
        assertThat(rental.endDate).isEqualTo(
            OffsetDateTime.parse(request.endDate!!).truncatedTo(ChronoUnit.MICROS)
        )
        assertThat(rental.status).isEqualTo(RentalStatus.NEW)
        assertThat(rental.entityVersion).isEqualTo(0L)

        assertCarQuantity(rental.carId, carQuantityBefore - 1)
        assertApplicationEvents("RentalMade")
    }
}