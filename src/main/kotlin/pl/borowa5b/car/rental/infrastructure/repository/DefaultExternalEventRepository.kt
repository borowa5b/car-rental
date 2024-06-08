package pl.borowa5b.car.rental.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import pl.borowa5b.car.rental.domain.model.ExternalEvent
import pl.borowa5b.car.rental.domain.model.vo.ExternalEventId
import pl.borowa5b.car.rental.domain.repository.ExternalEventRepository
import pl.borowa5b.car.rental.infrastructure.entity.ExternalEventEntity

@Component
class DefaultExternalEventRepository(private val repository: SpringJpaExternalEventRepository) :
    ExternalEventRepository {

    override fun save(externalEvent: ExternalEvent): ExternalEventEntity =
        repository.save(ExternalEventEntity.fromDomain(externalEvent))

    override fun exists(id: ExternalEventId): Boolean = repository.existsById(id.value)
}

@Repository
interface SpringJpaExternalEventRepository : JpaRepository<ExternalEventEntity, String>