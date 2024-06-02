package pl.borowa5b.car.rental.domain.generator

import pl.borowa5b.car.rental.domain.model.vo.CarId

interface CarIdGenerator {

    fun generate(): CarId
}