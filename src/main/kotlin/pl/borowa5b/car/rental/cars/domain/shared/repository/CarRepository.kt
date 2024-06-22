package pl.borowa5b.car.rental.cars.domain.shared.repository

import pl.borowa5b.car.rental.cars.domain.model.Car
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.cars.domain.vo.Brand

interface CarRepository {

    fun existsBy(carId: CarId): Boolean

    fun existsBy(brand: Brand, model: String, generation: String, year: Int, color: String): Boolean

    fun save(car: Car): Car

    fun findBy(carId: CarId): Car?
}