package pl.borowa5b.car.rental.cars.application.request

import io.swagger.v3.oas.annotations.media.Schema
import pl.borowa5b.car.rental.cars.domain.command.EditCarCommand
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.cars.domain.vo.Brand
import pl.borowa5b.car.rental.shared.domain.exception.validation.ValidationExceptionHandler
import pl.borowa5b.car.rental.shared.domain.exception.validation.Validator
import java.math.BigDecimal

data class EditCarRequest(
    @Schema(
        type = "string",
        requiredMode = Schema.RequiredMode.REQUIRED,
        description = "Car identifier",
        example = "CAR2354353"
    )
    val carId: String?,
    @Schema(type = "string", description = "Car brand", example = "FORD")
    val brand: String?,
    @Schema(type = "string", description = "Car model", example = "Mondeo")
    val model: String?,
    @Schema(type = "string", description = "Model generation", example = "MK3")
    val generation: String?,
    @Schema(type = "number", description = "Car production year", example = "2006")
    val productionYear: Int?,
    @Schema(type = "string", description = "Car color", example = "Black metallic")
    val color: String?,
    @Schema(type = "number", description = "Rental price per day", example = "100")
    val pricePerDay: BigDecimal?,
    @Schema(type = "number", description = "Number of cars for rent", example = "10")
    val quantity: Int?
) {

    fun validate(validationExceptionHandler: ValidationExceptionHandler) {
        Validator.isNotNull(carId, "carId", validationExceptionHandler)

        carId?.let {
            CarId.validate(it, "carId", validationExceptionHandler)
        }

        brand?.let {
            Validator.isValidEnumValue<Brand>(it, "brand", validationExceptionHandler)
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

        pricePerDay?.let {
            Validator.isGreaterThan(it, BigDecimal.ZERO, "pricePerDay", validationExceptionHandler)
        }

        quantity?.let {
            Validator.isGreaterThan(it, -1, "quantity", validationExceptionHandler)
        }
    }

    fun toCommand(): EditCarCommand = EditCarCommand(
        carId = CarId(carId!!),
        brand = brand?.let { Brand.valueOf(it) },
        model = model,
        generation = generation,
        productionYear = productionYear,
        color = color,
        pricePerDay = pricePerDay,
        quantity = quantity
    )
}
