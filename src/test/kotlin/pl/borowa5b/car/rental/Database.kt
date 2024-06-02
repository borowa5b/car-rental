package pl.borowa5b.car.rental

import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.infrastructure.repository.SpringJpaCarRepository
import pl.borowa5b.car.rental.infrastructure.repository.SpringJpaCustomerRepository
import pl.borowa5b.car.rental.infrastructure.repository.SpringJpaRentalRepository

@Component
class Database(
    private val rentalRepository: SpringJpaRentalRepository,
    private val carRepository: SpringJpaCarRepository,
    private val customerRepository: SpringJpaCustomerRepository
) {

    fun prepare() {
        rentalRepository.deleteAll()
        carRepository.deleteAll()
        customerRepository.deleteAll()
    }
}