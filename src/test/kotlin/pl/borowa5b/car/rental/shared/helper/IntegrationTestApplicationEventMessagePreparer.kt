package pl.borowa5b.car.rental.shared.helper

import com.fasterxml.jackson.databind.ObjectMapper
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.cars.domain.shared.vo.ValueObjects.carId
import pl.borowa5b.car.rental.events.domain.model.DomainObjects.applicationEvent
import pl.borowa5b.car.rental.rentals.domain.event.DomainEventObjects.rentalMadeEvent

object IntegrationTestApplicationEventMessagePreparer {

    private val objectMapper = IntegrationTestEnvironment.getBean(ObjectMapper::class.java)

    fun prepareRentalMadeEventMessage(carId: CarId = carId()) = objectMapper.writeValueAsString(
        applicationEvent(payload = objectMapper.writeValueAsString(rentalMadeEvent(carId = carId)))
    )
}