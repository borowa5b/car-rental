package pl.borowa5b.car.rental.shared.helper

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import pl.borowa5b.car.rental.cars.infrastructure.entity.CarEntity
import pl.borowa5b.car.rental.customers.infrastructure.entity.CustomerEntity
import pl.borowa5b.car.rental.events.infrastructure.entity.ApplicationEventEntity
import pl.borowa5b.car.rental.events.infrastructure.entity.ExternalEventEntity
import pl.borowa5b.car.rental.rentals.infrastructure.entity.RentalEntity

@Component
class Database(
    private val rentalRepository: TestSpringRentalRepository,
    private val carRepository: TestSpringCarRepository,
    private val customerRepository: TestSpringCustomerRepository,
    private val applicationEventRepository: TestSpringApplicationEventRepository,
    private val externalEventRepository: TestSpringExternalEventRepository
) {

    fun prepare() {
        rentalRepository.deleteAll()
        carRepository.deleteAll()
        customerRepository.deleteAll()
        applicationEventRepository.deleteAll()
        externalEventRepository.deleteAll()
    }
}

@Repository
interface TestSpringCarRepository : JpaRepository<CarEntity, String>

@Repository
interface TestSpringCustomerRepository : JpaRepository<CustomerEntity, String>

@Repository
interface TestSpringRentalRepository : JpaRepository<RentalEntity, String>

@Repository
interface TestSpringApplicationEventRepository : JpaRepository<ApplicationEventEntity, String>

@Repository
interface TestSpringExternalEventRepository : JpaRepository<ExternalEventEntity, String>