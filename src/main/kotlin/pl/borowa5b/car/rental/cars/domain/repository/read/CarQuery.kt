package pl.borowa5b.car.rental.cars.domain.repository.read

import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.cars.domain.vo.Brand
import java.math.BigDecimal

data class CarQuery(
    val id: CarId?,
    val brand: Brand?,
    val model: String?,
    val generation: String?,
    val productionYear: Int?,
    val color: String?,
    val pricePerDayFrom: BigDecimal?,
    val pricePerDayTo: BigDecimal?,
    val quantityFrom: Int?,
    val quantityTo: Int?
)