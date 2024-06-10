package pl.borowa5b.car.rental.rentals.domain.repository.read

import pl.borowa5b.car.rental.rentals.domain.vo.RentalId
import pl.borowa5b.car.rental.shared.domain.pagination.Page
import pl.borowa5b.car.rental.shared.domain.pagination.Pageable

interface RentalFinder {

    fun findBy(query: RentalQuery, page: Page): Pageable<RentalDetails>

    fun findBy(rentalId: RentalId): RentalDetails
}