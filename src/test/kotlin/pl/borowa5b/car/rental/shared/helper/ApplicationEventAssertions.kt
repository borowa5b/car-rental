package pl.borowa5b.car.rental.shared.helper

import org.assertj.core.api.Assertions.assertThat
import pl.borowa5b.car.rental.events.domain.vo.ApplicationEventStatus

object ApplicationEventAssertions {

    private val applicationEventRepository: TestSpringApplicationEventRepository =
        IntegrationTestEnvironment.getBean(TestSpringApplicationEventRepository::class.java)


    fun assertApplicationEvents(vararg types: String) {
        val applicationEvents = applicationEventRepository.findAll()
        assertThat(applicationEvents.map { it.type }).containsAll(types.toList())
        assertThat(applicationEvents.map { it.status }).allMatch { it == ApplicationEventStatus.NEW }
    }
}