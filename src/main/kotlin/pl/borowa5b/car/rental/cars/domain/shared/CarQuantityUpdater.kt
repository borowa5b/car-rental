package pl.borowa5b.car.rental.cars.domain.shared

import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId

interface CarQuantityUpdater {

    fun increase(carId: CarId)
    fun decrease(carId: CarId)
}