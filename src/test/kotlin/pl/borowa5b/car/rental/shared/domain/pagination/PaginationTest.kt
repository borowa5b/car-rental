package pl.borowa5b.car.rental.shared.domain.pagination

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Sort
import pl.borowa5b.car.rental.shared.domain.exception.ValidationErrorException
import pl.borowa5b.car.rental.shared.domain.pagination.Page
import pl.borowa5b.car.rental.shared.domain.pagination.Pagination

class PaginationTest {

    @Test
    fun `should validate`() {
        // given
        val pageNumber = 1
        val pageSize = 10

        // when
        val result = catchThrowable { Pagination.validate(pageNumber, pageSize) }

        // then
        assertThat(result).isNull()
    }

    @Test
    fun `should not validate when page number is invalid`() {
        // given
        val pageNumber = -1
        val pageSize = 10

        // when
        val result = catchThrowable { Pagination.validate(pageNumber, pageSize) }

        // then
        assertThat(result).isExactlyInstanceOf(ValidationErrorException::class.java)
    }

    @Test
    fun `should not validate when page size is invalid`() {
        // given
        val pageNumber = 1
        val pageSize = -1

        // when
        val result = catchThrowable { Pagination.validate(pageNumber, pageSize) }

        // then
        assertThat(result).isExactlyInstanceOf(ValidationErrorException::class.java)
    }

    @Test
    fun `should create pagination of data and page`() {
        // given
        val pageNumber = 1
        val pageSize = 10
        val data = (1..11).toList()
        val order = Sort.Order.desc("test")

        // when
        val result = Pagination.of(data, Page(pageNumber, pageSize, order))

        // then
        assertThat(result.number).isEqualTo(pageNumber)
        assertThat(result.size).isEqualTo(pageSize)
        assertThat(result.hasNext).isTrue()
        assertThat(result.hasPrevious).isFalse()
    }
}