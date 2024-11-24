package pl.borowa5b.car.rental.customers.infrastructure.repository.read

object CustomerTableDefinition {

    const val TABLE_NAME = "customer"

    object Column {
        const val ID = "id"
        const val NAME = "name"
        const val SURNAME = "surname"
        const val EMAIL = "email"
        const val PHONE_NUMBER = "phone_number"
        const val ADDRESS = "address"
        const val DOCUMENT_NUMBER = "document_number"
        const val CREATION_DATE = "creation_date"
        const val MODIFICATION_DATE = "modification_date"
        const val ENTITY_VERSION = "entity_version"
    }
}