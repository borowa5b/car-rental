package pl.borowa5b.car.rental.cars.domain

import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.cars.domain.command.AddCarCommand
import pl.borowa5b.car.rental.cars.domain.event.CarAddedEvent
import pl.borowa5b.car.rental.cars.domain.exception.CarAlreadyExistsException
import pl.borowa5b.car.rental.cars.domain.generator.CarIdGenerator
import pl.borowa5b.car.rental.cars.domain.model.Car
import pl.borowa5b.car.rental.cars.domain.shared.repository.CarRepository
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.events.domain.shared.ApplicationEventPublisher

@Component
class CarAdder(
    private val carIdGenerator: CarIdGenerator,
    private val carRepository: CarRepository,
    private val applicationEventPublished: ApplicationEventPublisher
) {

    fun add(command: AddCarCommand): CarId {
        validate(command)

        val car = Car(
            id = carIdGenerator.generate(),
            brand = command.brand,
            model = command.getModel(),
            generation = command.getGeneration(),
            productionYear = command.productionYear,
            color = command.getColor(),
            pricePerDay = command.pricePerDay,
            quantity = command.quantity
        )
        carRepository.save(car)
        applicationEventPublished.publish(CarAddedEvent(car))
        return car.id
    }

    private fun validate(command: AddCarCommand) {
        if (carRepository.existsBy(
                command.brand,
                command.getModel(),
                command.getGeneration(),
                command.productionYear,
                command.getColor()
            )
        ) {
            throw CarAlreadyExistsException(
                command.brand,
                command.getModel(),
                command.productionYear,
                command.getColor()
            )
        }
    }
}