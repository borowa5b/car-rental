package pl.borowa5b.car.rental.rentals.domain.repository

import pl.borowa5b.car.rental.rentals.domain.model.Rental
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.rentals.domain.vo.RentalId
import pl.borowa5b.car.rental.rentals.infrastructure.entity.RentalEntity

interface RentalRepository {

    fun save(rental: Rental): Rental

    fun findById(id: RentalId): Rental?

    fun hasActiveRentals(customerId: CustomerId): Boolean
}