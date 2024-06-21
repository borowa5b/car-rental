package pl.borowa5b.car.rental.shared.infrastructure.filter

import jakarta.servlet.FilterChain
import nl.altindag.log.LogCaptor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.http.HttpMethod
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.util.FastByteArrayOutputStream
import org.springframework.web.util.ContentCachingRequestWrapper
import pl.borowa5b.car.rental.shared.domain.vo.Role
import java.io.ByteArrayOutputStream


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
        }
        val response = MockHttpServletResponse().apply {
            writer.write("{ \"param\": \"value\" }")
            writer.flush()
        }
        val filterChain = mock(FilterChain::class.java)
        whenever(filterChain.doFilter(any(), any())).thenAnswer { answer ->
            val mockRequest = answer.arguments[0] as ContentCachingRequestWrapper
            val mockResponse = answer.arguments[1] as MockHttpServletResponse
            val requestContent = ReflectionTestUtils.getField(mockRequest, "cachedContent") as FastByteArrayOutputStream
            val responseContent = ReflectionTestUtils.getField(mockResponse, "content") as ByteArrayOutputStream
            requestContent.write(responseContent.toByteArray())
            requestContent.flush()
            null
        }

        // when
        requestLoggingFilter.doFilter(request, response, filterChain)

        // then
        val infoLogs = logCaptor.infoLogs
        assertThat(infoLogs).hasSize(1)

        val requestMessage = infoLogs[0]
        assertThat(requestMessage).isEqualTo("Incoming request: POST./test | Role: ROLE_ADMIN | Query params: param=value | Request Body: { \"param\": \"value\" }")
    }
}