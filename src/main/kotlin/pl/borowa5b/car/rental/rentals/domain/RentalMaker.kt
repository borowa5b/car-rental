package pl.borowa5b.car.rental.rentals.domain

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import pl.borowa5b.car.rental.cars.domain.shared.CarQuantityUpdater
import pl.borowa5b.car.rental.cars.domain.shared.exception.CarNotFoundException
import pl.borowa5b.car.rental.cars.domain.shared.repository.CarRepository
import pl.borowa5b.car.rental.customers.domain.shared.exception.CustomerNotFoundException
import pl.borowa5b.car.rental.customers.domain.shared.repository.CustomerRepository
import pl.borowa5b.car.rental.events.domain.shared.ApplicationEventPublisher
import pl.borowa5b.car.rental.rentals.domain.command.CalculateRentalCommand
import pl.borowa5b.car.rental.rentals.domain.command.MakeRentalCommand
import pl.borowa5b.car.rental.rentals.domain.event.RentalMadeEvent
import pl.borowa5b.car.rental.rentals.domain.exception.CustomerHasActiveRentalsException
import pl.borowa5b.car.rental.rentals.domain.generator.RentalIdGenerator
import pl.borowa5b.car.rental.rentals.domain.model.Rental
import pl.borowa5b.car.rental.rentals.domain.shared.repository.RentalRepository
import pl.borowa5b.car.rental.rentals.domain.vo.RentalId
import pl.borowa5b.car.rental.rentals.domain.vo.RentalStatus

@Component
class RentalMaker(
    private val rentalIdGenerator: RentalIdGenerator,
    private val rentalPriceCalculator: RentalPriceCalculator,
    private val rentalRepository: RentalRepository,
    private val carRepository: CarRepository,
    private val customerRepository: CustomerRepository,
    private val carQuantityUpdater: CarQuantityUpdater,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    @Transactional
    fun make(command: MakeRentalCommand): RentalId {
        validate(command)

        val startDate = command.startDate
        val endDate = command.endDate
        val calculateRentalCommand = CalculateRentalCommand(startDate, endDate)
        val rentalPrice = rentalPriceCalculator.calculate(calculateRentalCommand)
        val rental = Rental(
            id = rentalIdGenerator.generate(),
            carId = command.carId,
            customerId = command.customerId,
            startDate = startDate,
            endDate = endDate,
            price = rentalPrice,
            status = RentalStatus.NEW
        )
        carQuantityUpdater.decrease(command.carId)
        rentalRepository.save(rental)
        applicationEventPublisher.publish(RentalMadeEvent(rental))
        return rental.id
    }

    private fun validate(command: MakeRentalCommand) {
        if (!carRepository.existsBy(command.carId)) {
            throw CarNotFoundException(command.carId)
        }
        if (!customerRepository.exists(command.customerId)) {
            throw CustomerNotFoundException(command.customerId)
        }
        if (rentalRepository.hasActiveRentals(command.customerId)) {
            throw CustomerHasActiveRentalsException(command.customerId)
        }
    }
}