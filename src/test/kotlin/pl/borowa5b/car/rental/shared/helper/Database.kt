package pl.borowa5b.car.rental.shared.helper

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import pl.borowa5b.car.rental.cars.infrastructure.entity.CarEntity
import pl.borowa5b.car.rental.customers.infrastructure.entity.CustomerEntity
import pl.borowa5b.car.rental.rentals.infrastructure.entity.RentalEntity

@Component
class Database(
    private val rentalRepository: TestSpringRentalRepository,
    private val carRepository: TestSpringCarRepository,
    private val customerRepository: TestSpringCustomerRepository
) {

    fun prepare() {
        rentalRepository.deleteAll()
        carRepository.deleteAll()
        customerRepository.deleteAll()
    }
}

@Repository
interface TestSpringCarRepository : JpaRepository<CarEntity, String>

@Repository
interface TestSpringCustomerRepository : JpaRepository<CustomerEntity, String>

@Repository
interface TestSpringRentalRepository : JpaRepository<RentalEntity, String>