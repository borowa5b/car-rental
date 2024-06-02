package pl.borowa5b.car.rental.domain.exception

import org.zalando.problem.Status
import pl.borowa5b.car.rental.domain.DomainException
import pl.borowa5b.car.rental.domain.model.vo.CarId

class CarNotFoundException(carId: CarId) : DomainException(Status.NOT_FOUND, "Car with id ${carId.value} not found")