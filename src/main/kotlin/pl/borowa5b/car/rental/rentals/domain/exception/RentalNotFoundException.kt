package pl.borowa5b.car.rental.rentals.domain.exception

import org.zalando.problem.Status
import pl.borowa5b.car.rental.shared.domain.DomainException
import pl.borowa5b.car.rental.rentals.domain.vo.RentalId

class RentalNotFoundException(rentalId: RentalId) :
    DomainException(Status.NOT_FOUND, "Rental with id ${rentalId.value} not found")