package pl.borowa5b.car.rental.events.infrastructure.generator

import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.events.domain.generator.EventIdGenerator
import pl.borowa5b.car.rental.events.domain.vo.EventId
import pl.borowa5b.car.rental.shared.domain.generator.IdGenerator

@Component
class DefaultEventIdGenerator(private val idGenerator: IdGenerator) : EventIdGenerator {

    override fun generate(): EventId = EventId(idGenerator.generate(EventId.PREFIX))
}