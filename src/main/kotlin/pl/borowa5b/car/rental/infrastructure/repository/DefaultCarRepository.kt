package pl.borowa5b.car.rental.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import pl.borowa5b.car.rental.domain.model.vo.CarId
import pl.borowa5b.car.rental.domain.repository.CarRepository
import pl.borowa5b.car.rental.infrastructure.entity.CarEntity

@Component
class DefaultCarRepository(private val repository: SpringJpaCarRepository) : CarRepository {

    override fun exists(carId: CarId): Boolean = repository.existsById(carId.value)
}

@Repository
interface SpringJpaCarRepository : JpaRepository<CarEntity, String>