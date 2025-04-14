package pl.borowa5b.car.rental.events.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import pl.borowa5b.car.rental.events.domain.model.ApplicationEvent
import pl.borowa5b.car.rental.events.domain.repository.ApplicationEventRepository
import pl.borowa5b.car.rental.events.infrastructure.entity.ApplicationEventEntity

@Component
class DefaultApplicationEventRepository(private val repository: SpringJpaApplicationEventRepository) :
    ApplicationEventRepository {

    override fun save(applicationEvent: ApplicationEvent): ApplicationEvent =
        repository.save(ApplicationEventEntity.fromDomain(applicationEvent)).toDomain()

    override fun findAll(): List<ApplicationEvent> = repository.findAll().map { it.toDomain() }

    override fun findToPublish(): List<ApplicationEvent> =
        repository.findByPublishedOnDateIsNull().map { it.toDomain() }
}

@Repository
interface SpringJpaApplicationEventRepository : JpaRepository<ApplicationEventEntity, String> {

    fun findByPublishedOnDateIsNull(): List<ApplicationEventEntity>
}