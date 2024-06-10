package pl.borowa5b.car.rental.rentals.infrastructure.entity

import jakarta.persistence.*
import pl.borowa5b.car.rental.rentals.domain.model.Rental
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.rentals.domain.vo.RentalId
import pl.borowa5b.car.rental.rentals.domain.vo.RentalStatus
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Entity(name = "rental")
data class RentalEntity(
    @Id
    val id: String,
    val carId: String,
    val customerId: String,
    @Enumerated(EnumType.STRING)
    val status: RentalStatus,
    val price: BigDecimal,
    val startDate: OffsetDateTime,
    val endDate: OffsetDateTime,

    @Version
    val entityVersion: Long
) {

    val creationDate: OffsetDateTime = OffsetDateTime.now(ZoneOffset.UTC)
    var modificationDate: OffsetDateTime = OffsetDateTime.now(ZoneOffset.UTC)

    @PreUpdate
    fun preUpdate() {
        modificationDate = OffsetDateTime.now(ZoneOffset.UTC)
    }

    fun toDomain(): Rental = Rental(
        RentalId(id),
        CarId(carId),
        CustomerId(customerId),
        status,
        price,
        startDate,
        endDate,
        entityVersion
    )

    companion object {

        fun fromDomain(rental: Rental): RentalEntity = rental.let {
            RentalEntity(
                it.id.value,
                it.carId.value,
                it.customerId.value,
                it.status,
                it.price,
                it.startDate,
                it.endDate,
                it.version
            )
        }
    }
}