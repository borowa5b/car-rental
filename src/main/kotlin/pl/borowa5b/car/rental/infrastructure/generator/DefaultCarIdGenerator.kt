package pl.borowa5b.car.rental.infrastructure.generator

import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.domain.generator.CarIdGenerator
import pl.borowa5b.car.rental.domain.generator.IdGenerator
import pl.borowa5b.car.rental.domain.model.CarId

@Component
class DefaultCarIdGenerator(private val idGenerator: IdGenerator) : CarIdGenerator {

    override fun generate(): CarId = CarId(idGenerator.generate(CarId.PREFIX))
}