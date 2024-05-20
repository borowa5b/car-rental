package pl.borowa5b.car.rental.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import pl.borowa5b.car.rental.domain.model.Rental
import pl.borowa5b.car.rental.domain.model.RentalId
import pl.borowa5b.car.rental.domain.repository.RentalRepository
import pl.borowa5b.car.rental.infrastructure.entity.RentalEntity
import kotlin.jvm.optionals.getOrNull

@Component
class DefaultRentalRepository(private val repository: SpringJpaRentalRepository) : RentalRepository {

    override fun save(rental: Rental): RentalEntity = repository.save(RentalEntity.fromDomain(rental))

    override fun findById(id: RentalId): Rental? = repository.findById(id.value).map { it.toDomain() }.getOrNull()
}

@Repository
interface SpringJpaRentalRepository : JpaRepository<RentalEntity, String>