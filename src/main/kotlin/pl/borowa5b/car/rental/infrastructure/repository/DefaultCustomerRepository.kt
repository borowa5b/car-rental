package pl.borowa5b.car.rental.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import pl.borowa5b.car.rental.domain.model.Customer
import pl.borowa5b.car.rental.domain.model.vo.CustomerId
import pl.borowa5b.car.rental.domain.repository.CustomerRepository
import pl.borowa5b.car.rental.infrastructure.entity.CustomerEntity

@Component
class DefaultCustomerRepository(private val repository: SpringJpaCustomerRepository) : CustomerRepository {

    override fun exists(customerId: CustomerId): Boolean = repository.existsById(customerId.value)

    override fun save(customer: Customer) = repository.save(CustomerEntity.fromDomain(customer))

    override fun findById(id: CustomerId): Customer? = repository.findById(id.value).map { it.toDomain() }.orElse(null)
}

@Repository
interface SpringJpaCustomerRepository : JpaRepository<CustomerEntity, String>