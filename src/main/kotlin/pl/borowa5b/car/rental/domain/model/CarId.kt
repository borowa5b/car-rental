package pl.borowa5b.car.rental.domain.model

data class CarId(val value: String) {

    init {
        validate(value)
    }

    companion object {

        const val PREFIX: String = "CAR"

        fun validate(value: String) {
            if (!value.startsWith(PREFIX)) {
                throw IllegalArgumentException("Car id must start with $PREFIX")
            }
        }
    }
}