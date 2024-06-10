package pl.borowa5b.car.rental.shared.infrastructure.filter

import jakarta.servlet.http.HttpServletRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockFilterChain
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import pl.borowa5b.car.rental.shared.infrastructure.filter.TrailingSlashRedirectFilter

class TrailingSlashRedirectFilterTest {

    @Test
    fun `should remove trailing slashes from request`() {
        // given
        val filter = TrailingSlashRedirectFilter()
        val requestURI = "/test//"
        val expectedRequestURI = "/test"
        val request = object : MockHttpServletRequest() {
            override fun getRequestURI(): String = requestURI
        }
        val response = MockHttpServletResponse()
        val chain = MockFilterChain()

        // when
        filter.doFilter(request, response, chain)

        // then
        val forwardedRequest = chain.request as HttpServletRequest
        assertThat(forwardedRequest.requestURI).isEqualTo(expectedRequestURI)
    }

    @Test
    fun `should not remove trailing slashes from request`() {
        // given
        val filter = TrailingSlashRedirectFilter()
        val requestURI = "/test"
        val request = object : MockHttpServletRequest() {
            override fun getRequestURI(): String = requestURI
        }
        val response = MockHttpServletResponse()
        val chain = MockFilterChain()

        // when
        filter.doFilter(request, response, chain)

        // then
        val forwardedRequest = chain.request as HttpServletRequest
        assertThat(forwardedRequest.requestURI).isEqualTo(requestURI)
    }
}