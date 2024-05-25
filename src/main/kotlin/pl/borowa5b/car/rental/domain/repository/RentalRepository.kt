package pl.borowa5b.car.rental.domain.repository

import pl.borowa5b.car.rental.domain.model.CustomerId
import pl.borowa5b.car.rental.domain.model.Rental
import pl.borowa5b.car.rental.domain.model.RentalId
import pl.borowa5b.car.rental.infrastructure.entity.RentalEntity

interface RentalRepository {

    fun save(rental: Rental): RentalEntity

    fun findById(id: RentalId): Rental?

    fun hasActiveRentals(customerId: CustomerId): Boolean
}