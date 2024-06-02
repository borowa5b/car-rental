package pl.borowa5b.car.rental.application.endpoint

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import pl.borowa5b.car.rental.Database
import pl.borowa5b.car.rental.IntegrationTest
import pl.borowa5b.car.rental.domain.exception.RentalNotFoundException
import pl.borowa5b.car.rental.domain.model.DomainObjects.rentalId
import pl.borowa5b.car.rental.infrastructure.entity.EntityObjects.rentalEntity
import pl.borowa5b.car.rental.infrastructure.repository.SpringJpaRentalRepository
import java.time.temporal.ChronoUnit

@IntegrationTest
class GetRentalEndpointIT {

    @Autowired
    private lateinit var database: Database

    @Autowired
    private lateinit var rentalRepository: SpringJpaRentalRepository

    @Autowired
    private lateinit var endpoint: GetRentalEndpoint

    @BeforeEach
    fun `before each`() {
        database.prepare()
    }

    @Test
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