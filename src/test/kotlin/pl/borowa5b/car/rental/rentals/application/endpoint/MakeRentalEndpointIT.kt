package pl.borowa5b.car.rental.rentals.application.endpoint

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser
import pl.borowa5b.car.rental.cars.domain.shared.model.DomainObjects.car
import pl.borowa5b.car.rental.cars.domain.shared.repository.CarRepository
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.customers.domain.shared.model.DomainObjects.customer
import pl.borowa5b.car.rental.customers.domain.shared.repository.CustomerRepository
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.events.domain.shared.repository.ApplicationEventRepository
import pl.borowa5b.car.rental.events.domain.shared.vo.ApplicationEventStatus
import pl.borowa5b.car.rental.rentals.application.request.RequestObjects.makeRentalRequest
import pl.borowa5b.car.rental.rentals.domain.repository.RentalRepository
import pl.borowa5b.car.rental.rentals.domain.vo.RentalId
import pl.borowa5b.car.rental.rentals.domain.vo.RentalStatus
import pl.borowa5b.car.rental.shared.domain.vo.Role
import pl.borowa5b.car.rental.shared.helper.Database
import pl.borowa5b.car.rental.shared.helper.IntegrationTest
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

@IntegrationTest
class MakeRentalEndpointIT {

    @Autowired
    private lateinit var database: Database

    @Autowired
    private lateinit var endpoint: MakeRentalEndpoint

    @Autowired
    private lateinit var rentalRepository: RentalRepository

    @Autowired
    private lateinit var carRepository: CarRepository

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var applicationEventRepository: ApplicationEventRepository

    @BeforeEach
    fun `before each`() {
        database.prepare()
    }

    @Test
    @WithMockUser(roles = [Role.USER])
    fun `should make rental`() {
        // given
        val request = makeRentalRequest()
        val carId = CarId(request.carId!!)
        val customerId = CustomerId(request.customerId!!)
        carRepository.save(car(id = carId))
        customerRepository.save(customer(id = customerId))

        // when
        val result = endpoint.make(request)

        // then
        val rental = rentalRepository.findById(RentalId(result.body!!.rentalId))!!
        assertThat(rental.carId).isEqualTo(carId)
        assertThat(rental.customerId).isEqualTo(customerId)
        assertThat(rental.startDate).isEqualTo(
            OffsetDateTime.parse(request.startDate!!).truncatedTo(ChronoUnit.MICROS)
        )
        assertThat(rental.endDate).isEqualTo(
            OffsetDateTime.parse(request.endDate!!).truncatedTo(ChronoUnit.MICROS)
        )
        assertThat(rental.status).isEqualTo(RentalStatus.NEW)
        assertThat(rental.version).isEqualTo(0L)

        val eventsPublished = applicationEventRepository.findAll()
        assertThat(eventsPublished).hasSize(1)

        val eventPublished = eventsPublished[0]
        assertThat(eventPublished.type).isEqualTo("RentalMade")
        assertThat(eventPublished.status).isEqualTo(ApplicationEventStatus.NEW)
    }
}