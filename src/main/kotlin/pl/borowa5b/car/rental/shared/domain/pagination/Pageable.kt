package pl.borowa5b.car.rental.shared.domain.pagination

data class Pageable<T>(val data: List<T>, val pagination: Pagination) {

    companion object {

        fun <T> of(data: List<T>, page: Page) = Pageable(data.take(page.size), Pagination.of(data, page))
    }
}