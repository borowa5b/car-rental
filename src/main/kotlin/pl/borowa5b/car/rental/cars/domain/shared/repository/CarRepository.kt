package pl.borowa5b.car.rental.cars.domain.shared.repository

import pl.borowa5b.car.rental.cars.domain.model.Car
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId

interface CarRepository {

    fun exists(carId: CarId): Boolean

    fun save(car: Car): Car
}