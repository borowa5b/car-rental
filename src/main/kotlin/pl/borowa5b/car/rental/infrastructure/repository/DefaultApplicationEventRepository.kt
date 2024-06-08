package pl.borowa5b.car.rental.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import pl.borowa5b.car.rental.domain.model.ApplicationEvent
import pl.borowa5b.car.rental.domain.repository.ApplicationEventRepository
import pl.borowa5b.car.rental.infrastructure.entity.ApplicationEventEntity

@Component
class DefaultApplicationEventRepository(private val repository: SpringJpaApplicationEventRepository) :
    ApplicationEventRepository {

    override fun save(applicationEvent: ApplicationEvent): ApplicationEventEntity =
        repository.save(ApplicationEventEntity.fromDomain(applicationEvent))
}

@Repository
interface SpringJpaApplicationEventRepository : JpaRepository<ApplicationEventEntity, String>