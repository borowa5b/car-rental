package pl.borowa5b.car.rental.application.endpoint

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import pl.borowa5b.car.rental.IntegrationTest
import pl.borowa5b.car.rental.application.request.RequestObjects.calculateRentalRequest
import pl.borowa5b.car.rental.domain.PriceConfiguration
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@IntegrationTest
class CalculateRentalEndpointIT {

    @Autowired
    private lateinit var priceConfiguration: PriceConfiguration

    @Autowired
    private lateinit var endpoint: CalculateRentalEndpoint

    @Test
    fun `should calculate rental price`() {
        // given
        val request = calculateRentalRequest(
            startDate = OffsetDateTime.now(ZoneOffset.UTC).plusDays(1).format(DateTimeFormatter.ISO_DATE_TIME),
            endDate = OffsetDateTime.now(ZoneOffset.UTC).plusDays(2).format(DateTimeFormatter.ISO_DATE_TIME)
        )

        // when
        val result = endpoint.calculate(request)

        // then
        val body = result.body!!
        assertThat(body.price).isEqualTo(priceConfiguration.pricePerDay)
    }
}