package pl.borowa5b.car.rental.events.infrastructure

import com.fasterxml.jackson.core.JacksonException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.events.domain.EventPuller
import pl.borowa5b.car.rental.events.domain.model.ExternalEvent

@Component
@ConditionalOnProperty(prefix = "car-rental", name = ["file-event-puller-enabled"], havingValue = "true")
class FileEventPuller(
    private val objectMapper: ObjectMapper,
    private val externalEventProcessor: ExternalEventProcessor
) : EventPuller {

    override fun pull() {
        val eventsString = this::class.java.getResource("/event/customers.json")?.readText(Charsets.UTF_8)
        val typeReference = object : TypeReference<List<ExternalEvent>>() {}
        val events = try {
            objectMapper.readValue(eventsString, typeReference)
        } catch (_: JacksonException) {
            listOf()
        }
        events.forEach { externalEventProcessor.process(it) }
    }
}