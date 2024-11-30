package pl.borowa5b.car.rental.shared.application.endpoint.dictionaries

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.test.context.support.WithMockUser
import pl.borowa5b.car.rental.shared.domain.vo.Role
import pl.borowa5b.car.rental.shared.helper.IntegrationTest

@IntegrationTest
class GetDictionariesEndpointTest {

    @Autowired
    private lateinit var getDictionariesEndpoint: GetDictionariesEndpoint

    @Test
    @WithMockUser(roles = [Role.ADMIN])
    fun `should get dictionaries`() {
        // given

        // when
        val result = getDictionariesEndpoint.getDictionaries()

        // then
        assertThat(result).hasSize(4)
    }

    @Test
    @WithMockUser(roles = [Role.USER])
    fun `should not get dictionaries when using invalid role`() {
        // given

        // when
        val result = catchThrowable { getDictionariesEndpoint.getDictionaries() }

        // then
        assertThat(result).isExactlyInstanceOf(AccessDeniedException::class.java)
    }
}