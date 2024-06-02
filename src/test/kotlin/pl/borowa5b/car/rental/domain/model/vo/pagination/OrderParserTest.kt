package pl.borowa5b.car.rental.domain.model.vo.pagination

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Sort.Direction
import pl.borowa5b.car.rental.domain.exception.ValidationErrorException
import pl.borowa5b.car.rental.domain.model.DomainObjects.order

class OrderParserTest {

    @Test
    fun `should parse`() {
        // given
        val order = "creationDate,DESC"
        val expectedOrder = order(direction = Direction.DESC, property = "creationDate")

        // when
        val result = OrderParser.parse(order)

        // then
        assertThat(result).isEqualTo(expectedOrder)
    }

    @Test
    fun `should not parse`() {
        // given
        val order = "creationDate"

        // when
        val result = catchThrowable { OrderParser.parse(order) }

        // then
        assertThat(result).isExactlyInstanceOf(ValidationErrorException::class.java)
    }

    @Test
    fun `should validate`() {
        // given
        val order = "creationDate,DESC"

        // when
        val result = catchThrowable { OrderParser.validate(order) }

        // then
        assertThat(result).isNull()
    }

    @Test
    fun `should not validate`() {
        // given
        val order = "creationDate"

        // when
        val result = catchThrowable { OrderParser.validate(order) }

        // then
        assertThat(result).isExactlyInstanceOf(ValidationErrorException::class.java)
    }
}