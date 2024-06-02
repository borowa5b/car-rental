package pl.borowa5b.car.rental.domain.repository

import pl.borowa5b.car.rental.domain.model.vo.CarId

interface CarRepository {

    fun exists(carId: CarId): Boolean
}