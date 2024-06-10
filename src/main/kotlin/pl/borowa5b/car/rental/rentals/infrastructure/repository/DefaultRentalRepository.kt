package pl.borowa5b.car.rental.rentals.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.rentals.domain.model.Rental
import pl.borowa5b.car.rental.rentals.domain.repository.RentalRepository
import pl.borowa5b.car.rental.rentals.domain.vo.RentalId
import pl.borowa5b.car.rental.rentals.domain.vo.RentalStatus
import pl.borowa5b.car.rental.rentals.infrastructure.entity.RentalEntity
import kotlin.jvm.optionals.getOrNull

@Component
class DefaultRentalRepository(private val repository: SpringJpaRentalRepository) : RentalRepository {

    override fun save(rental: Rental): Rental = repository.save(RentalEntity.fromDomain(rental)).toDomain()

    override fun findById(id: RentalId): Rental? = repository.findById(id.value).map { it.toDomain() }.getOrNull()

    override fun hasActiveRentals(customerId: CustomerId): Boolean =
        repository.existsByCustomerIdAndNotInStatus(customerId.value, RentalStatus.ENDED)
}

@Repository
interface SpringJpaRentalRepository : JpaRepository<RentalEntity, String> {

    @Query(
        """
            SELECT exists(
                SELECT r 
                FROM rental r
                WHERE r.customerId = :customerId
                AND r.status != :status
            )
        """
    )
    fun existsByCustomerIdAndNotInStatus(customerId: String, status: RentalStatus): Boolean
}