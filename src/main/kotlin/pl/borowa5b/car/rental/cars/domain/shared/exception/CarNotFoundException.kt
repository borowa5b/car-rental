package pl.borowa5b.car.rental.cars.domain.shared.exception

import org.zalando.problem.Status
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.shared.domain.DomainException

class CarNotFoundException(carId: CarId) : DomainException(Status.NOT_FOUND, "Car with id ${carId.value} not found")