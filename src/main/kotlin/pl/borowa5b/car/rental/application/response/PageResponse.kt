package pl.borowa5b.car.rental.application.response

import pl.borowa5b.car.rental.domain.model.vo.pagination.Pagination

data class PageResponse<T>(val data: List<T>, val pagination: Pagination)