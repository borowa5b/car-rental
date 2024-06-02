package pl.borowa5b.car.rental.infrastructure.generator

import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.domain.generator.CustomerIdGenerator
import pl.borowa5b.car.rental.domain.generator.IdGenerator
import pl.borowa5b.car.rental.domain.model.vo.CustomerId

@Component
class DefaultCustomerIdGenerator(private val idGenerator: IdGenerator) : CustomerIdGenerator {

    override fun generate(): CustomerId = CustomerId(idGenerator.generate(CustomerId.PREFIX))
}