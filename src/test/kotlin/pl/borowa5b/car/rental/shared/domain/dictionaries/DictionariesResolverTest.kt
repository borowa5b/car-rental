package pl.borowa5b.car.rental.shared.domain.dictionaries

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DictionariesResolverTest {

    @Test
    fun `should resolve dictionaries`() {
        // given

        // when
        val result = DictionariesResolver.resolve()

        // then
        assertThat(result).hasSize(4)
    }
}