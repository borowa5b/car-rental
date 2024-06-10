package pl.borowa5b.car.rental.rentals.infrastructure.generator

import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.rentals.domain.generator.RentalIdGenerator
import pl.borowa5b.car.rental.rentals.domain.vo.RentalId
import pl.borowa5b.car.rental.shared.domain.generator.IdGenerator

@Component
class DefaultRentalIdGenerator(private val idGenerator: IdGenerator) : RentalIdGenerator {

    override fun generate(): RentalId = RentalId(idGenerator.generate(RentalId.PREFIX))
}