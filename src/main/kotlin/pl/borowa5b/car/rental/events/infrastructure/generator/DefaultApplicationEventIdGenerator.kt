package pl.borowa5b.car.rental.events.infrastructure.generator

import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.events.domain.generator.ApplicationEventIdGenerator
import pl.borowa5b.car.rental.events.domain.vo.ApplicationEventId
import pl.borowa5b.car.rental.shared.domain.generator.IdGenerator

@Component
class DefaultApplicationEventIdGenerator(private val idGenerator: IdGenerator) : ApplicationEventIdGenerator {

    override fun generate(): ApplicationEventId = ApplicationEventId(idGenerator.generate(ApplicationEventId.PREFIX))
}