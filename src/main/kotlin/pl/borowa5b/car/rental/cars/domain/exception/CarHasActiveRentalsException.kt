package pl.borowa5b.car.rental.cars.domain.exception

import org.zalando.problem.Status
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.shared.domain.DomainException

class CarHasActiveRentalsException(carId: CarId) :
    DomainException(Status.CONFLICT, "Car with id ${carId.value} has active rentals.")