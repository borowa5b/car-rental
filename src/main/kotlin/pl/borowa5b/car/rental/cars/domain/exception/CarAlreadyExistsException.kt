package pl.borowa5b.car.rental.cars.domain.exception

import org.zalando.problem.Status
import pl.borowa5b.car.rental.cars.domain.vo.Brand
import pl.borowa5b.car.rental.shared.domain.DomainException

class CarAlreadyExistsException(brand: Brand, model: String, productionYear: Int, generation: String) :
    DomainException(Status.CONFLICT, "Car ${brand.name} $model $generation from $productionYear already exists.")