package pl.borowa5b.car.rental.shared.helper

import org.assertj.core.api.Assertions.assertThat
import pl.borowa5b.car.rental.events.domain.vo.ApplicationEventStatus
import pl.borowa5b.car.rental.events.domain.vo.ExternalEventStatus

object IntegrationTestAssertions {

    private val applicationEventRepository: TestSpringApplicationEventRepository =
        IntegrationTestEnvironment.getBean(TestSpringApplicationEventRepository::class.java)

    private val externalEventRepository: TestSpringExternalEventRepository =
        IntegrationTestEnvironment.getBean(TestSpringExternalEventRepository::class.java)

    private val carRepository: TestSpringCarRepository =
        IntegrationTestEnvironment.getBean(TestSpringCarRepository::class.java)


    fun assertApplicationEvents(vararg types: String, status: ApplicationEventStatus = ApplicationEventStatus.FAILED) {
        val applicationEvents = applicationEventRepository.findAll()
        assertThat(applicationEvents.map { it.type }).containsAll(types.toList())
        assertThat(applicationEvents.map { it.status }).allMatch { it == status }
    }

    fun assertExternalEvents(vararg types: String, status: ExternalEventStatus) {
        val externalEvents = externalEventRepository.findAll()
        assertThat(externalEvents.map { it.type }).containsAll(types.toList())
        assertThat(externalEvents.map { it.status }).allMatch { it == status }
    }

    fun assertCarQuantity(carId: String, expectedCarQuantity: Int) {
        assertThat(carRepository.findById(carId).orElse(null).quantity).isEqualTo(expectedCarQuantity)
    }
}