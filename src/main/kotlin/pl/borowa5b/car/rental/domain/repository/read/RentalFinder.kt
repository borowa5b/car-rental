package pl.borowa5b.car.rental.domain.repository.read

import pl.borowa5b.car.rental.domain.model.vo.RentalId
import pl.borowa5b.car.rental.domain.model.vo.pagination.Page
import pl.borowa5b.car.rental.domain.model.vo.pagination.Pageable

interface RentalFinder {

    fun findBy(query: RentalQuery, page: Page): Pageable<RentalDetails>

    fun findBy(rentalId: RentalId): RentalDetails
}