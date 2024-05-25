package pl.borowa5b.car.rental.domain

import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.domain.command.MakeRentalCommand
import pl.borowa5b.car.rental.domain.generator.RentalIdGenerator
import pl.borowa5b.car.rental.domain.model.Rental
import pl.borowa5b.car.rental.domain.model.RentalId
import pl.borowa5b.car.rental.domain.model.RentalStatus
import pl.borowa5b.car.rental.domain.repository.CarRepository
import pl.borowa5b.car.rental.domain.repository.CustomerRepository
import pl.borowa5b.car.rental.domain.repository.RentalRepository

@Component
class RentalMaker(
    private val rentalIdGenerator: RentalIdGenerator,
    private val rentalPriceCalculator: RentalPriceCalculator,
    private val rentalRepository: RentalRepository,
    private val carRepository: CarRepository,
    private val customerRepository: CustomerRepository
) {

    fun make(command: MakeRentalCommand): RentalId {
        validate(command)

        val startDate = command.startDate
        val endDate = command.endDate
        val rentalPrice = rentalPriceCalculator.calculate(startDate, endDate)
        val rental = Rental(
            id = rentalIdGenerator.generate(),
            carId = command.carId,
            customerId = command.customerId,
            startDate = startDate,
            endDate = endDate,
            price = rentalPrice,
            status = RentalStatus.NEW
        )
        val rentalEntity = rentalRepository.save(rental)
        return RentalId(rentalEntity.id)
    }

    private fun validate(command: MakeRentalCommand) {
        if (!carRepository.exists(command.carId)) {
            throw IllegalArgumentException("Car with id ${command.carId} does not exist")
        }
        if (!customerRepository.exists(command.customerId)) {
            throw IllegalArgumentException("Customer with id ${command.customerId} does not exist")
        }
        if (rentalRepository.hasActiveRentals(command.customerId)) {
            throw IllegalArgumentException("Customer with id ${command.customerId} already has rentals")
        }
    }
}