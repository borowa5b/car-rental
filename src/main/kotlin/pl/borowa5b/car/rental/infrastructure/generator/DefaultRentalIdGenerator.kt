package pl.borowa5b.car.rental.infrastructure.generator

import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.domain.generator.IdGenerator
import pl.borowa5b.car.rental.domain.generator.RentalIdGenerator
import pl.borowa5b.car.rental.domain.model.vo.RentalId

@Component
class DefaultRentalIdGenerator(private val idGenerator: IdGenerator) : RentalIdGenerator {

    override fun generate(): RentalId = RentalId(idGenerator.generate(RentalId.PREFIX))
}