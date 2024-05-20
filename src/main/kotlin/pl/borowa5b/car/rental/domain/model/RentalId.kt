package pl.borowa5b.car.rental.domain.model

data class RentalId(val value: String) {

    init {
        validate(value)
    }

    companion object {

        const val PREFIX: String = "RNT"

        fun validate(value: String) {
            if (!value.startsWith(PREFIX)) {
                throw IllegalArgumentException("Rental id must start with $PREFIX")
            }
        }
    }
}