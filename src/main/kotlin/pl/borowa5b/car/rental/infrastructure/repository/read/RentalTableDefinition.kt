package pl.borowa5b.car.rental.infrastructure.repository.read

object RentalTableDefinition {

    const val TABLE_NAME = "rental"

    object Column {
        const val ID = "id"
        const val CAR_ID = "car_id"
        const val CUSTOMER_ID = "customer_id"
        const val STATUS = "status"
        const val PRICE = "price"
        const val START_DATE = "start_date"
        const val END_DATE = "end_date"
        const val CREATION_DATE = "CREATION_DATE"
        const val MODIFICATION_DATE = "MODIFICATION_DATE"
        const val ENTITY_VERSION = "ENTITY_VERSION"
    }

    fun sortDefinition() = mapOf(
        Column.CREATION_DATE to "creationDate"
    )
}