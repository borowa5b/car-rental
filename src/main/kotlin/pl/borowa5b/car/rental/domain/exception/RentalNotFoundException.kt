package pl.borowa5b.car.rental.domain.exception

import org.zalando.problem.Status
import pl.borowa5b.car.rental.domain.DomainException
import pl.borowa5b.car.rental.domain.model.vo.RentalId

class RentalNotFoundException(rentalId: RentalId) :
    DomainException(Status.NOT_FOUND, "Rental with id ${rentalId.value} not found")