package pl.borowa5b.car.rental.cars.domain.generator

import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId

interface CarIdGenerator {

    fun generate(): CarId
}