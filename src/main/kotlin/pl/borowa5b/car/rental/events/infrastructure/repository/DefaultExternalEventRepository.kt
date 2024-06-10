package pl.borowa5b.car.rental.events.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import pl.borowa5b.car.rental.events.domain.model.ExternalEvent
import pl.borowa5b.car.rental.events.domain.repository.ExternalEventRepository
import pl.borowa5b.car.rental.events.domain.shared.vo.ExternalEventId
import pl.borowa5b.car.rental.events.infrastructure.entity.ExternalEventEntity

@Component
class DefaultExternalEventRepository(private val repository: SpringJpaExternalEventRepository) :
    ExternalEventRepository {

    override fun save(externalEvent: ExternalEvent): ExternalEvent =
        repository.save(ExternalEventEntity.fromDomain(externalEvent)).toDomain()

    override fun exists(id: ExternalEventId): Boolean = repository.existsById(id.value)
}

@Repository
interface SpringJpaExternalEventRepository : JpaRepository<ExternalEventEntity, String>