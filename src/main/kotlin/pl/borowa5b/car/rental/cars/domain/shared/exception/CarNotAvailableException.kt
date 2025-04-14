package pl.borowa5b.car.rental.cars.domain.shared.exception

import org.zalando.problem.Status
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.shared.domain.DomainException

class CarNotAvailableException(carId: CarId) :
    DomainException(Status.CONFLICT, "Car with id ${carId.value} not available for rent")