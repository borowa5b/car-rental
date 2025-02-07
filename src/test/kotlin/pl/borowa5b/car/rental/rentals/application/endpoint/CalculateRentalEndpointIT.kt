package pl.borowa5b.car.rental.rentals.application.endpoint

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser
import pl.borowa5b.car.rental.rentals.application.request.RequestObjects.calculateRentalRequest
import pl.borowa5b.car.rental.rentals.domain.PriceConfiguration
import pl.borowa5b.car.rental.shared.domain.vo.Role
import pl.borowa5b.car.rental.shared.helper.IntegrationTest
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
    @WithMockUser(roles = [Role.RENTALS])
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