package pl.borowa5b.car.rental.shared.application.request

object RequestObjects {

    fun pageRequest(number: Int? = 1, size: Int? = 10): PageRequest = PageRequest(
        pageNumber = number,
        pageSize = size
    )
}