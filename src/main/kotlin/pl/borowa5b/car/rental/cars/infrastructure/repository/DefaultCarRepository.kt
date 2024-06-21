package pl.borowa5b.car.rental.cars.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import pl.borowa5b.car.rental.cars.domain.model.Car
import pl.borowa5b.car.rental.cars.domain.shared.repository.CarRepository
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.cars.domain.vo.Brand
import pl.borowa5b.car.rental.cars.infrastructure.entity.CarEntity

@Component
class DefaultCarRepository(private val repository: SpringJpaCarRepository) : CarRepository {

    override fun existsBy(carId: CarId): Boolean = repository.existsById(carId.value)

    override fun existsBy(brand: Brand, model: String, generation: String, year: Int, color: String): Boolean =
        repository.existsByBrandAndModelAndGenerationAndYearAndColor(brand, model, generation, year, color)

    override fun save(car: Car): Car = repository.save(CarEntity.fromDomain(car)).toDomain()
}

@Repository
interface SpringJpaCarRepository : JpaRepository<CarEntity, String> {

    fun existsByBrandAndModelAndGenerationAndYearAndColor(
        brand: Brand,
        model: String,
        generation: String,
        year: Int,
        color: String
    ): Boolean
}