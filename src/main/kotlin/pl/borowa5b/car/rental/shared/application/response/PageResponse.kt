package pl.borowa5b.car.rental.shared.application.response

import pl.borowa5b.car.rental.shared.domain.pagination.Pagination

data class PageResponse<T>(val data: List<T>, val pagination: Pagination)