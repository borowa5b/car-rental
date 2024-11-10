package pl.borowa5b.car.rental.events.infrastructure.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.events.domain.model.DomainObjects.externalEvent
import pl.borowa5b.car.rental.events.infrastructure.entity.EntityObjects.externalEventEntity

class ExternalEventEntityTest {

    @Test
    fun `should convert from domain object`() {
        // given
        val externalEvent = externalEvent()

        // when
        val result = ExternalEventEntity.fromDomain(externalEvent)

        // then
        assertThat(result.id).isEqualTo(externalEvent.id.value)
        assertThat(result.type).isEqualTo(externalEvent.type)
        assertThat(result.version).isEqualTo(externalEvent.version)
        assertThat(result.status).isEqualTo(externalEvent.status)
        assertThat(result.payload).isEqualTo(externalEvent.payload)
        assertThat(result.errorMessage).isEqualTo(externalEvent.errorMessage)
        assertThat(result.processedOnDate).isEqualTo(externalEvent.processedOnDate)
        assertThat(result.entityVersion).isEqualTo(externalEvent.entityVersion)
    }

    @Test
    fun `should convert to domain object`() {
        // given
        val externalEventEntity = externalEventEntity()

        // when
        val result = externalEventEntity.toDomain()

        // then
        assertThat(result.id.value).isEqualTo(externalEventEntity.id)
        assertThat(result.type).isEqualTo(externalEventEntity.type)
        assertThat(result.version).isEqualTo(externalEventEntity.version)
        assertThat(result.status).isEqualTo(externalEventEntity.status)
        assertThat(result.payload).isEqualTo(externalEventEntity.payload)
        assertThat(result.errorMessage).isEqualTo(externalEventEntity.errorMessage)
        assertThat(result.processedOnDate).isEqualTo(externalEventEntity.processedOnDate)
        assertThat(result.entityVersion).isEqualTo(externalEventEntity.entityVersion)
    }
}