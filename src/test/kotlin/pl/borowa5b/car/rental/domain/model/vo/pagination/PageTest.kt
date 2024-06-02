package pl.borowa5b.car.rental.domain.model.vo.pagination

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Sort
import pl.borowa5b.car.rental.domain.exception.ValidationErrorException
import pl.borowa5b.car.rental.domain.model.DomainObjects.order

class PageTest {

    @Test
    fun `should validate`() {
        // given
        val pageNumber = 1
        val pageSize = 10

        // when
        val result = catchThrowable { Page.validate(pageNumber, pageSize) }

        // then
        assertThat(result).isNull()
    }

    @Test
    fun `should not validate when page number is invalid`() {
        // given
        val pageNumber = -1
        val pageSize = 10

        // when
        val result = catchThrowable { Page.validate(pageNumber, pageSize) }

        // then
        assertThat(result).isExactlyInstanceOf(ValidationErrorException::class.java)
    }

    @Test
    fun `should not validate when page size is invalid`() {
        // given
        val pageNumber = 1
        val pageSize = -1

        // when
        val result = catchThrowable { Page.validate(pageNumber, pageSize) }

        // then
        assertThat(result).isExactlyInstanceOf(ValidationErrorException::class.java)
    }

    @Test
    fun `should get order as string`() {
        // given
        val pageNumber = 1
        val pageSize = 10
        val property = "property"
        val direction = "ASC"
        val order = order(direction = Sort.Direction.ASC, property = property)
        val expectedOrderAsString = "$property $direction".uppercase()

        // when
        val result = Page(pageNumber, pageSize, order).getOrderAsString()

        // then
        assertThat(result).isEqualTo(expectedOrderAsString)
    }
}