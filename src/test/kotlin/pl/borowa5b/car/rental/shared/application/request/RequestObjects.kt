package pl.borowa5b.car.rental.shared.application.request

object RequestObjects {

    fun pageRequest(number: Int? = 1, size: Int? = 10): PageRequest = PageRequest(
        number = number,
        size = size
    )
}