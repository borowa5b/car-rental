package pl.borowa5b.car.rental.infrastructure.generator

import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.domain.generator.ApplicationEventIdGenerator
import pl.borowa5b.car.rental.domain.generator.IdGenerator
import pl.borowa5b.car.rental.domain.model.vo.ApplicationEventId

@Component
class DefaultApplicationEventIdGenerator(private val idGenerator: IdGenerator) : ApplicationEventIdGenerator {

    override fun generate(): ApplicationEventId = ApplicationEventId(idGenerator.generate(ApplicationEventId.PREFIX))
}