package pl.borowa5b.car.rental.shared.domain.pagination

import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction

object PaginationObjects {

    fun order(
        direction: Direction = Direction.DESC,
        property: String = "id"
    ): Sort.Order = Sort.Order(direction, property)

    fun page(
        number: Int = 1,
        size: Int = 10,
        order: Sort.Order = order()
    ): Page = Page(
        number = number,
        size = size,
        order = order
    )

}