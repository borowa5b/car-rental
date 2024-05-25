package pl.borowa5b.car.rental.application.endpoint

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import pl.borowa5b.car.rental.IntegrationTest
import pl.borowa5b.car.rental.application.request.RequestObjects.makeRentalRequest
import pl.borowa5b.car.rental.domain.model.RentalStatus
import pl.borowa5b.car.rental.infrastructure.entity.EntityObjects.carEntity
import pl.borowa5b.car.rental.infrastructure.entity.EntityObjects.customerEntity
import pl.borowa5b.car.rental.infrastructure.repository.SpringJpaCarRepository
import pl.borowa5b.car.rental.infrastructure.repository.SpringJpaCustomerRepository
import pl.borowa5b.car.rental.infrastructure.repository.SpringJpaRentalRepository
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

@IntegrationTest
class MakeRentalEndpointIT {

    @Autowired
    private lateinit var endpoint: MakeRentalEndpoint

    @Autowired
    private lateinit var rentalRepository: SpringJpaRentalRepository

    @Autowired
    private lateinit var carRepository: SpringJpaCarRepository

    @Autowired
    private lateinit var customerRepository: SpringJpaCustomerRepository

    @Test
    fun `should make rental`() {
        // given
        val request = makeRentalRequest()
        val carId = request.carId!!
        val customerId = request.customerId!!
        carRepository.save(carEntity(id = carId))
        customerRepository.save(customerEntity(id = customerId))

        // when
        val result = endpoint.make(request)

        // then
        val createdRentalOptional = rentalRepository.findById(result.body!!.rentalId)
        assertThat(createdRentalOptional).isPresent()

        val createdRental = createdRentalOptional.get()
        assertThat(createdRental.carId).isEqualTo(carId)
        assertThat(createdRental.customerId).isEqualTo(request.customerId)
        assertThat(createdRental.startDate).isEqualTo(
            OffsetDateTime.parse(request.startDate!!).truncatedTo(ChronoUnit.MICROS)
        )
        assertThat(createdRental.endDate).isEqualTo(
            OffsetDateTime.parse(request.endDate!!).truncatedTo(ChronoUnit.MICROS)
        )
        assertThat(createdRental.status).isEqualTo(RentalStatus.NEW)
        assertThat(createdRental.entityVersion).isEqualTo(0L)
    }
}