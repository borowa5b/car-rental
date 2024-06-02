package pl.borowa5b.car.rental.application.endpoint

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import pl.borowa5b.car.rental.Database
import pl.borowa5b.car.rental.IntegrationTest
import pl.borowa5b.car.rental.application.request.RequestObjects.getRentalsFilter
import pl.borowa5b.car.rental.application.request.RequestObjects.pageRequest
import pl.borowa5b.car.rental.domain.model.DomainObjects.customerId
import pl.borowa5b.car.rental.infrastructure.entity.EntityObjects.rentalEntity
import pl.borowa5b.car.rental.infrastructure.repository.SpringJpaRentalRepository

@IntegrationTest
class GetRentalsEndpointIT {

    @Autowired
    private lateinit var database: Database

    @Autowired
    private lateinit var rentalRepository: SpringJpaRentalRepository

    @Autowired
    private lateinit var endpoint: GetRentalsEndpoint

    @BeforeEach
    fun `before each`() {
        database.prepare()
    }

    @Test
    fun `should get rentals`() {
        // given
        val customerId = customerId().value
        val rentalEntity1 = rentalEntity(customerId = customerId)
        val rentalEntity2 = rentalEntity(customerId = customerId)
        val rentalEntity3 = rentalEntity(customerId = customerId().value)
        rentalRepository.save(rentalEntity1)
        rentalRepository.save(rentalEntity2)
        rentalRepository.save(rentalEntity3)
        val filter = getRentalsFilter(customerId = customerId)
        val pageRequest = pageRequest()

        // when
        val result = endpoint.getRentals(filter, pageRequest)

        // then
        val data = result.data
        assertThat(data).hasSize(2)
        assertThat(data[0].id).isEqualTo(rentalEntity2.id)
        assertThat(data[1].id).isEqualTo(rentalEntity1.id)
    }

    @Test
    fun `should get rentals by page`() {
        // given
        val customerId = customerId().value
        val rentalEntity1 = rentalEntity(customerId = customerId)
        val rentalEntity2 = rentalEntity(customerId = customerId)
        val rentalEntity3 = rentalEntity(customerId = customerId)
        rentalRepository.save(rentalEntity1)
        rentalRepository.save(rentalEntity2)
        rentalRepository.save(rentalEntity3)
        val filter = getRentalsFilter(customerId = customerId)
        val pageRequest = pageRequest(number = 2, size = 2)

        // when
        val result = endpoint.getRentals(filter, pageRequest)

        // then
        val data = result.data
        assertThat(data).hasSize(1)
        assertThat(data[0].id).isEqualTo(rentalEntity1.id)
    }
}