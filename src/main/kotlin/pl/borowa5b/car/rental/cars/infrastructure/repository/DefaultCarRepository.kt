package pl.borowa5b.car.rental.cars.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import pl.borowa5b.car.rental.cars.domain.model.Car
import pl.borowa5b.car.rental.cars.domain.shared.repository.CarRepository
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.cars.infrastructure.entity.CarEntity

@Component
class DefaultCarRepository(private val repository: SpringJpaCarRepository) : CarRepository {

    override fun exists(carId: CarId): Boolean = repository.existsById(carId.value)

    override fun save(car: Car): Car = repository.save(CarEntity.fromDomain(car)).toDomain()
}

@Repository
interface SpringJpaCarRepository : JpaRepository<CarEntity, String>