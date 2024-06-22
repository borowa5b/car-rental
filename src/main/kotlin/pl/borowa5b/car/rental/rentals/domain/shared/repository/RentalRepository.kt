package pl.borowa5b.car.rental.rentals.domain.shared.repository

import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.rentals.domain.model.Rental
import pl.borowa5b.car.rental.rentals.domain.vo.RentalId
import java.time.OffsetDateTime

interface RentalRepository {

    fun save(rental: Rental): Rental

    fun findById(id: RentalId): Rental?

    fun hasActiveRentals(customerId: CustomerId): Boolean

    fun hasActiveRentals(carId: CarId): Boolean

    fun findToStart(currentDate: OffsetDateTime): RentalId?

    fun findToEnd(currentDate: OffsetDateTime): RentalId?
}