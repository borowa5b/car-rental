package pl.borowa5b.car.rental.cars.domain.repository.read

import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.shared.domain.pagination.Page
import pl.borowa5b.car.rental.shared.domain.pagination.Pageable

interface CarFinder {

    fun findBy(query: CarQuery, page: Page): Pageable<CarDetails>

    fun findBy(carId: CarId): CarDetails
}