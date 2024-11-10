package pl.borowa5b.car.rental.rentals.application.endpoint

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser
import pl.borowa5b.car.rental.customers.domain.shared.vo.ValueObjects.customerId
import pl.borowa5b.car.rental.rentals.application.filter.FilterObjects.getRentalsFilter
import pl.borowa5b.car.rental.rentals.infrastructure.entity.EntityObjects.rentalEntity
import pl.borowa5b.car.rental.rentals.infrastructure.repository.SpringJpaRentalRepository
import pl.borowa5b.car.rental.shared.application.request.RequestObjects.pageRequest
import pl.borowa5b.car.rental.shared.domain.vo.Role
import pl.borowa5b.car.rental.shared.helper.IntegrationTest

@IntegrationTest
class GetRentalsEndpointIT {

    @Autowired
    private lateinit var rentalRepository: SpringJpaRentalRepository

    @Autowired
    private lateinit var endpoint: GetRentalsEndpoint

    @Test
    @WithMockUser(roles = [Role.USER])
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
    @WithMockUser(roles = [Role.USER])
    fun `should get rentals another page`() {
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

        val pagination = result.pagination
        assertThat(pagination.number).isEqualTo(2)
        assertThat(pagination.size).isEqualTo(2)
    }
}