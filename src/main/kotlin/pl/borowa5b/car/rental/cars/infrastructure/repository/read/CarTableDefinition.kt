package pl.borowa5b.car.rental.cars.infrastructure.repository.read

object CarTableDefinition {

    const val TABLE_NAME = "car"

    object Column {
        const val ID = "id"
        const val BRAND = "brand"
        const val MODEL = "model"
        const val GENERATION = "generation"
        const val PRODUCTION_YEAR = "production_year"
        const val COLOR = "color"
        const val PRICE_PER_DAY = "price_per_day"
        const val QUANTITY = "quantity"
        const val CREATION_DATE = "creation_date"
        const val MODIFICATION_DATE = "modification_date"
        const val ENTITY_VERSION = "entity_version"
    }
}