package pl.borowa5b.car.rental.cars.infrastructure.entity

import jakarta.persistence.*
import pl.borowa5b.car.rental.cars.domain.model.Car
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import java.time.OffsetDateTime
import java.time.ZoneOffset

@Entity(name = "car")
data class CarEntity(
    @Id
    val id: String,
    val brand: String,
    val model: String,
    val generation: String,
    @Column(name = "`year`")
    val year: Int,
    val color: String,
    val pricePerDay: Int,
    val quantity: Int,

    @Version
    val entityVersion: Long
) {

    val creationDate: OffsetDateTime = OffsetDateTime.now(ZoneOffset.UTC)
    var modificationDate: OffsetDateTime = OffsetDateTime.now(ZoneOffset.UTC)

    @PreUpdate
    fun preUpdate() {
        modificationDate = OffsetDateTime.now(ZoneOffset.UTC)
    }

    fun toDomain(): Car = Car(
        CarId(id),
        brand,
        model,
        generation,
        year,
        color,
        pricePerDay,
        quantity,
        entityVersion
    )

    companion object {

        fun fromDomain(car: Car): CarEntity = car.let {
            CarEntity(
                it.id.value,
                it.brand,
                it.model,
                it.generation,
                it.year,
                it.color,
                it.pricePerDay,
                it.quantity,
                it.version
            )
        }
    }
}