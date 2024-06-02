package pl.borowa5b.car.rental.infrastructure.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.PreUpdate
import jakarta.persistence.Version
import pl.borowa5b.car.rental.domain.model.Customer
import pl.borowa5b.car.rental.domain.model.vo.CustomerId
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Entity(name = "customer")
data class CustomerEntity(
    @Id
    val id: String,
    val name: String,
    val surname: String,
    val email: String,
    val phoneNumber: String,
    val address: String,
    val documentNumber: String,

    @Version
    val entityVersion: Long
) {

    val creationDate: OffsetDateTime = OffsetDateTime.now(ZoneOffset.UTC)
    var modificationDate: OffsetDateTime = OffsetDateTime.now(ZoneOffset.UTC)

    @PreUpdate
    fun preUpdate() {
        modificationDate = OffsetDateTime.now(ZoneOffset.UTC)
    }

    fun toDomain(): Customer = Customer(
        CustomerId(id),
        name,
        surname,
        email,
        phoneNumber,
        address,
        documentNumber,
        entityVersion
    )

    companion object {

        fun fromDomain(customer: Customer): CustomerEntity = customer.let {
            CustomerEntity(
                it.id.value,
                it.name,
                it.surname,
                it.email,
                it.phoneNumber,
                it.address,
                it.documentNumber,
                it.version
            )
        }
    }
}