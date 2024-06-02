package pl.borowa5b.car.rental.domain.model.vo.pagination

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.domain.model.DomainObjects.page

class PageableTest {

    @Test
    fun `should create pageable of data and page`() {
        // given
        val data = (1..11).toList()
        val page = page()

        // when
        val result = Pageable.of(data, page)

        // then
        assertThat(result.data).isEqualTo(data.take(page.size))

        val pagination = result.pagination
        assertThat(pagination.number).isEqualTo(page.number)
        assertThat(pagination.size).isEqualTo(page.size)
        assertThat(pagination.hasNext).isTrue()
        assertThat(pagination.hasPrevious).isFalse()
    }
}