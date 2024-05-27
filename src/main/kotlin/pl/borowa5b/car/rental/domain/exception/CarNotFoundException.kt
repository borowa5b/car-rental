package pl.borowa5b.car.rental.domain.exception

import pl.borowa5b.car.rental.domain.DomainException
import pl.borowa5b.car.rental.domain.model.CarId

class CarNotFoundException(carId: CarId) : DomainException("Car with id ${carId.value} not found")