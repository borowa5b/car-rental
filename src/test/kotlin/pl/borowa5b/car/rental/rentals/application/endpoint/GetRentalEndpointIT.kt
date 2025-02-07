package pl.borowa5b.car.rental.rentals.application.endpoint

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser
import pl.borowa5b.car.rental.rentals.domain.exception.RentalNotFoundException
import pl.borowa5b.car.rental.rentals.domain.vo.ValueObjects.rentalId
import pl.borowa5b.car.rental.rentals.infrastructure.entity.EntityObjects.rentalEntity
import pl.borowa5b.car.rental.rentals.infrastructure.repository.SpringJpaRentalRepository
import pl.borowa5b.car.rental.shared.domain.vo.Role
import pl.borowa5b.car.rental.shared.helper.IntegrationTest
import java.time.temporal.ChronoUnit

@IntegrationTest
class GetRentalEndpointIT {

    @Autowired
    private lateinit var rentalRepository: SpringJpaRentalRepository

    @Autowired
    private lateinit var endpoint: GetRentalEndpoint

    @Test
    @WithMockUser(roles = [Role.RENTALS])
    fun `should get rental`() {
        // given
        val rentalEntity = rentalEntity()
        rentalRepository.save(rentalEntity)

        // when
        val result = endpoint.getRental(rentalEntity.id)

        // then
        assertThat(result.id).isEqualTo(rentalEntity.id)
        assertThat(result.carId).isEqualTo(rentalEntity.carId)
        assertThat(result.customerId).isEqualTo(rentalEntity.customerId)
        assertThat(result.startDate).isEqualTo(rentalEntity.startDate)
        assertThat(result.endDate).isEqualTo(rentalEntity.endDate)
        assertThat(result.price.intValueExact()).isEqualTo(rentalEntity.price.intValueExact())
        assertThat(result.status).isEqualTo(rentalEntity.status.name)
        assertThat(result.creationDate).isEqualTo(rentalEntity.creationDate.truncatedTo(ChronoUnit.MICROS))
    }

    @Test
    @WithMockUser(roles = [Role.RENTALS])
    fun `should throw exception when rental not found`() {
        // given
        val rentalId = rentalId()

        // when
        val result = catchThrowable { endpoint.getRental(rentalId.value) }

        // then
        assertThat(result).isInstanceOf(RentalNotFoundException::class.java)
        assertThat(result.message).isEqualTo("Rental with id ${rentalId.value} not found")
    }
}