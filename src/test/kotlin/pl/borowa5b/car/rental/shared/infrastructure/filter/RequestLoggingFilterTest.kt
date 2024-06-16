package pl.borowa5b.car.rental.shared.infrastructure.filter

import nl.altindag.log.LogCaptor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpMethod
import org.springframework.mock.web.MockFilterChain
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import pl.borowa5b.car.rental.shared.domain.vo.Role

@ExtendWith(MockitoExtension::class)
class RequestLoggingFilterTest {

    @Test
    fun `should log request`() {
        // given
        val logCaptor = LogCaptor.forName(RequestLoggingFilter::class.simpleName)
        val requestLoggingFilter = RequestLoggingFilter()
        val request = object : MockHttpServletRequest() {
            override fun getRequestURI(): String = "/test"
            override fun getMethod(): String = HttpMethod.POST.name()
            override fun getQueryString(): String = "param=value"
        }.apply {
            addHeader("Role", Role.ROLE_ADMIN)
            setContent("{ \"param\": \"value\" }".toByteArray())
        }
        val response = MockHttpServletResponse()
        val filterChain = MockFilterChain()

        // when
        requestLoggingFilter.doFilter(request, response, filterChain)

        // then
        val infoLogs = logCaptor.infoLogs
        assertThat(infoLogs).hasSize(1)

        val requestMessage = infoLogs[0]
        assertThat(requestMessage).isEqualTo("Incoming request: POST./test | Role: ROLE_ADMIN | Query params: param=value | Request Body: { \"param\": \"value\" }")
    }
}