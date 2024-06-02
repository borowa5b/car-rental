package pl.borowa5b.car.rental.domain

import org.springframework.stereotype.Component
import pl.borowa5b.car.rental.domain.command.CalculateRentalCommand
import pl.borowa5b.car.rental.domain.command.MakeRentalCommand
import pl.borowa5b.car.rental.domain.exception.CarNotFoundException
import pl.borowa5b.car.rental.domain.exception.CustomerHasActiveRentalsException
import pl.borowa5b.car.rental.domain.exception.CustomerNotFoundException
import pl.borowa5b.car.rental.domain.generator.RentalIdGenerator
import pl.borowa5b.car.rental.domain.model.Rental
import pl.borowa5b.car.rental.domain.model.vo.RentalId
import pl.borowa5b.car.rental.domain.model.vo.RentalStatus
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
        val rentalEntity = rentalRepository.save(rental)
        return RentalId(rentalEntity.id)
    }

    private fun validate(command: MakeRentalCommand) {
        if (!carRepository.exists(command.carId)) {
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