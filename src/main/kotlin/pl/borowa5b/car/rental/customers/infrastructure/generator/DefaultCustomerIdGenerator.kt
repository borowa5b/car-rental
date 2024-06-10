package pl.borowa5b.car.rental.customers.infrastructure.generator

import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.customers.domain.generator.CustomerIdGenerator
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.shared.domain.generator.IdGenerator

@Component
class DefaultCustomerIdGenerator(private val idGenerator: IdGenerator) : CustomerIdGenerator {

    override fun generate(): CustomerId = CustomerId(idGenerator.generate(CustomerId.PREFIX))
}