package pl.borowa5b.car.rental.cars.domain

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import pl.borowa5b.car.rental.cars.domain.command.EditCarCommand
import pl.borowa5b.car.rental.cars.domain.event.CarEditedEvent
import pl.borowa5b.car.rental.cars.domain.exception.CarHasActiveRentalsException
import pl.borowa5b.car.rental.cars.domain.shared.exception.CarNotFoundException
import pl.borowa5b.car.rental.cars.domain.shared.repository.CarRepository
import pl.borowa5b.car.rental.events.domain.shared.ApplicationEventPublisher
import pl.borowa5b.car.rental.rentals.domain.shared.repository.RentalRepository

@Component
class CarEditor(
    private val carRepository: CarRepository,
    private val rentalRepository: RentalRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    @Transactional
    fun edit(command: EditCarCommand) {
        validate(command)

        val car = carRepository.findBy(command.carId)!!
        if (car.edit(command)) {
            carRepository.save(car)
            applicationEventPublisher.publish(CarEditedEvent(car))
        }
    }

    private fun validate(command: EditCarCommand) {
        val carId = command.carId
        if (!carRepository.existsBy(carId)) {
            throw CarNotFoundException(carId)
        }
        if (rentalRepository.hasActiveRentals(carId)) {
            throw CarHasActiveRentalsException(carId)
        }
    }
}