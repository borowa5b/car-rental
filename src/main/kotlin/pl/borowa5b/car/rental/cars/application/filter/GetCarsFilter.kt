package pl.borowa5b.car.rental.cars.application.filter

import io.swagger.v3.oas.annotations.Parameter
import org.springdoc.core.annotations.ParameterObject
import pl.borowa5b.car.rental.cars.domain.repository.read.CarQuery
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.cars.domain.vo.Brand
import pl.borowa5b.car.rental.shared.domain.exception.validation.ValidationExceptionHandler
import pl.borowa5b.car.rental.shared.domain.exception.validation.Validator
import java.math.BigDecimal

@ParameterObject
data class GetCarsFilter(
    @Parameter(description = "Car identifier", example = "CAR1214242443242")
    val id: String? = null,

    @Parameter(description = "Car brand", example = "FORD")
    val brand: String? = null,

    @Parameter(description = "Car model", example = "Mondeo")
    val model: String? = null,

    @Parameter(description = "Car generation", example = "MK3")
    val generation: String? = null,

    @Parameter(description = "Car production year", example = "2006")
    val productionYear: Int? = null,

    @Parameter(description = "Car paint color", example = "Metallic Black")
    val color: String? = null,

    @Parameter(description = "Rental start date from", example = "2022-01-01T12:00:00Z")
    val pricePerDayFrom: BigDecimal? = null,

    @Parameter(description = "Rental start date to", example = "2022-01-01T12:00:00Z")
    val pricePerDayTo: BigDecimal? = null,

    @Parameter(description = "Rental end date from", example = "2022-01-01T12:00:00Z")
    val quantityFrom: Int? = null,

    @Parameter(description = "Rental end date from", example = "2022-01-01T12:00:00Z")
    val quantityTo: Int? = null
) {

    fun validate(validationExceptionHandler: ValidationExceptionHandler) {
        id?.let {
            CarId.validate(it, "id", validationExceptionHandler)
        }
        brand?.let {
            Validator.isValidEnumValue<Brand>(brand, "brand", validationExceptionHandler)
        }
        model?.let {
            Validator.isNotBlank(it, "model", validationExceptionHandler)
        }
        generation?.let {
            Validator.isNotBlank(it, "generation", validationExceptionHandler)
        }
        productionYear?.let {
            Validator.isGreaterThan(it, 1900, "productionYear", validationExceptionHandler)
        }
        color?.let {
            Validator.isNotBlank(it, "color", validationExceptionHandler)
        }
        pricePerDayFrom?.let {
            Validator.isGreaterThan(it, BigDecimal.ZERO, "pricePerDayFrom", validationExceptionHandler)
        }
        pricePerDayTo?.let {
            Validator.isGreaterThan(it, BigDecimal.ZERO, "pricePerDayTo", validationExceptionHandler)
        }
        quantityFrom?.let {
            Validator.isGreaterThan(it, -1, "quantityFrom", validationExceptionHandler)
        }
        quantityTo?.let {
            Validator.isGreaterThan(it, -1, "quantityTo", validationExceptionHandler)
        }
    }

    fun toQuery(): CarQuery = CarQuery(
        id = id?.let { CarId(it) },
        brand = brand?.let { Brand.valueOf(it) },
        model = model?.lowercase(),
        generation = generation?.lowercase(),
        productionYear = productionYear,
        color = color?.lowercase(),
        pricePerDayFrom = pricePerDayFrom,
        pricePerDayTo = pricePerDayTo,
        quantityFrom = quantityFrom,
        quantityTo = quantityTo
    )
}