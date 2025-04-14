package pl.borowa5b.car.rental.events.domain.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.events.domain.model.ApplicationEvent
import pl.borowa5b.car.rental.events.domain.model.DomainObjects.applicationEvent
import java.time.OffsetDateTime

class ApplicationEventRepositoryTest {

    private val applicationEventRepository: ApplicationEventRepository = TestApplicationEventRepository()

    @BeforeEach
    fun `before each`() {
        (applicationEventRepository as TestApplicationEventRepository).deleteAll()
    }

    @Test
    fun `should save application event`() {
        // given
        val applicationEvent = applicationEvent()

        // when
        val result = applicationEventRepository.save(applicationEvent)

        // then
        assertThat(result.id).isEqualTo(applicationEvent.id)
    }

    @Test
    fun `should find all application events`() {
        // given
        val applicationEvent1 = applicationEvent()
        val applicationEvent2 = applicationEvent()
        applicationEventRepository.save(applicationEvent1)
        applicationEventRepository.save(applicationEvent2)

        // when
        val result = applicationEventRepository.findAll()

        // then
        assertThat(result).containsExactly(applicationEvent1, applicationEvent2)
    }

    @Test
    fun `should find all to publish`() {
        // given
        val applicationEvent1 = applicationEvent().apply { publishedOnDate = null }
        val applicationEvent2 = applicationEvent().apply { publishedOnDate = OffsetDateTime.now() }
        applicationEventRepository.save(applicationEvent1)
        applicationEventRepository.save(applicationEvent2)

        // when
        val result = applicationEventRepository.findToPublish()

        // then
        assertThat(result).containsExactly(applicationEvent1)
    }

    private class TestApplicationEventRepository(private val applicationEvents: ArrayList<ApplicationEvent> = ArrayList()) :
        ApplicationEventRepository {

        override fun save(applicationEvent: ApplicationEvent): ApplicationEvent {
            applicationEvents.add(applicationEvent)
            return applicationEvent
        }

        override fun findAll(): List<ApplicationEvent> = applicationEvents

        override fun findToPublish(): List<ApplicationEvent> = applicationEvents.filter { it.publishedOnDate == null }

        fun deleteAll() = applicationEvents.clear()
    }
}