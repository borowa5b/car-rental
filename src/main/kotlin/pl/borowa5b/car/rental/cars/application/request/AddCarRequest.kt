package pl.borowa5b.car.rental.cars.application.request

import io.swagger.v3.oas.annotations.media.Schema
import pl.borowa5b.car.rental.cars.domain.command.AddCarCommand
import pl.borowa5b.car.rental.cars.domain.vo.Brand
import pl.borowa5b.car.rental.shared.domain.exception.validation.ValidationExceptionHandler
import pl.borowa5b.car.rental.shared.domain.exception.validation.Validator
import java.math.BigDecimal

data class AddCarRequest(
    @Schema(type = "string", description = "Car brand", example = "FORD")
    val brand: String?,
    @Schema(type = "string", description = "Car model", example = "Mondeo")
    val model: String?,
    @Schema(type = "string", description = "Model generation", example = "MK3")
    val generation: String?,
    @Schema(type = "number", description = "Car year", example = "2006")
    val year: Int?,
    @Schema(type = "string", description = "Car color", example = "Black metallic")
    val color: String?,
    @Schema(type = "number", description = "Rental price per day", example = "100")
    val pricePerDay: BigDecimal?,
    @Schema(type = "number", description = "Number of cars for rent", example = "10")
    val quantity: Int?
) {

    fun validate(validationExceptionHandler: ValidationExceptionHandler) {
        Validator.isNotNullOrBlank(brand, "brand", validationExceptionHandler)
        Validator.isNotNullOrBlank(model, "model", validationExceptionHandler)
        Validator.isNotNullOrBlank(generation, "generation", validationExceptionHandler)
        Validator.isNotNull(year, "year", validationExceptionHandler)
        Validator.isNotNullOrBlank(color, "color", validationExceptionHandler)
        Validator.isNotNull(pricePerDay, "pricePerDay", validationExceptionHandler)
        Validator.isNotNull(quantity, "quantity", validationExceptionHandler)

        brand?.let {
            Validator.isValidEnumValue<Brand>(it, "brand", validationExceptionHandler)
        }

        quantity?.let {
            Validator.isGreaterThan(it, 1, "quantity", validationExceptionHandler)
        }
    }

    fun toCommand(): AddCarCommand = AddCarCommand(
        brand = Brand.valueOf(brand!!),
        model = model!!.lowercase(),
        generation = generation!!.lowercase(),
        year = year!!,
        color = color!!.lowercase(),
        pricePerDay = pricePerDay!!,
        quantity = quantity!!
    )
}
