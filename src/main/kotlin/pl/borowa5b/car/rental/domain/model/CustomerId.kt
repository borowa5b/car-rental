package pl.borowa5b.car.rental.domain.model

data class CustomerId(val value: String) {

    init {
        validate(value)
    }

    companion object {

        const val PREFIX: String = "CTR"

        fun validate(value: String) {
            if (!value.startsWith(PREFIX)) {
                throw IllegalArgumentException("Customer id must start with $PREFIX")
            }
        }
    }
}