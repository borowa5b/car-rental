package pl.borowa5b.car.rental.events.infrastructure.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.events.domain.model.DomainObjects.applicationEvent
import pl.borowa5b.car.rental.events.infrastructure.entity.EntityObjects.applicationEventEntity

class ApplicationEventEntityTest {

    @Test
    fun `should convert from domain object`() {
        // given
        val applicationEvent = applicationEvent()

        // when
        val result = ApplicationEventEntity.fromDomain(applicationEvent)

        // then
        assertThat(result.id).isEqualTo(applicationEvent.id.value)
        assertThat(result.type).isEqualTo(applicationEvent.type)
        assertThat(result.version).isEqualTo(applicationEvent.version)
        assertThat(result.status).isEqualTo(applicationEvent.status)
        assertThat(result.payload).isEqualTo(applicationEvent.payload)
        assertThat(result.publishedOnDate).isEqualTo(applicationEvent.publishedOnDate)
        assertThat(result.entityVersion).isEqualTo(applicationEvent.entityVersion)
    }

    @Test
    fun `should convert to domain object`() {
        // given
        val applicationEventEntity = applicationEventEntity()

        // when
        val result = applicationEventEntity.toDomain()

        // then
        assertThat(result.id.value).isEqualTo(applicationEventEntity.id)
        assertThat(result.type).isEqualTo(applicationEventEntity.type)
        assertThat(result.version).isEqualTo(applicationEventEntity.version)
        assertThat(result.status).isEqualTo(applicationEventEntity.status)
        assertThat(result.payload).isEqualTo(applicationEventEntity.payload)
        assertThat(result.publishedOnDate).isEqualTo(applicationEventEntity.publishedOnDate)
        assertThat(result.entityVersion).isEqualTo(applicationEventEntity.entityVersion)
    }
}