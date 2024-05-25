package pl.borowa5b.car.rental.infrastructure

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.springframework.http.HttpStatus
import org.springframework.web.context.request.WebRequest

class RestErrorHandlerTest {

    @Test
    fun `should handle exception`() {
        // given
        val restErrorHandler = RestErrorHandler()
        val exception = IllegalArgumentException("test")
        val request = mock(WebRequest::class.java)

        // when
        val result = restErrorHandler.handle(exception, request)!!

        // then
        assertThat(result.statusCode.value()).isEqualTo(HttpStatus.CONFLICT.value())
        assertThat(result.body).isEqualTo(
            """
            {
                "status": 409,
                "error": "Invalid request parameters",
                "message": "test"
            }
        """.trimIndent()
        )
    }
}