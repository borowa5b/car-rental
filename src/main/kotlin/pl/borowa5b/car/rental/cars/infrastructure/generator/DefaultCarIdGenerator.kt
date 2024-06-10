package pl.borowa5b.car.rental.cars.infrastructure.generator

import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.cars.domain.generator.CarIdGenerator
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.shared.domain.generator.IdGenerator

@Component
class DefaultCarIdGenerator(private val idGenerator: IdGenerator) : CarIdGenerator {

    override fun generate(): CarId = CarId(idGenerator.generate(CarId.PREFIX))
}