package pl.borowa5b.car.rental.rentals.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import pl.borowa5b.car.rental.rentals.domain.model.DomainObjects.rental
import pl.borowa5b.car.rental.rentals.domain.vo.RentalStatus
import java.time.OffsetDateTime
import java.time.ZoneOffset

class RentalTest {

    @Test
    fun `should start rental`() {
        // given
        val currentDate = OffsetDateTime.now(ZoneOffset.UTC)
        val rental = rental(status = RentalStatus.NEW)

        // when
        rental.start(currentDate)

        // then
        assertThat(rental.status).isEqualTo(RentalStatus.STARTED)
    }

    @Test
    fun `should throw exception when starting rental before its start date`() {
        // given
        val currentDate = OffsetDateTime.now(ZoneOffset.UTC)
        val rental = rental(status = RentalStatus.NEW, startDate = OffsetDateTime.now(ZoneOffset.UTC).plusDays(1))

        // when
        val result = catchThrowable { rental.start(currentDate) }

        // then
        assertThat(result).isInstanceOf(IllegalStateException::class.java)
            .hasMessage("Rental cannot be started when current date is before start date")
    }

    @ParameterizedTest
    @EnumSource(value = RentalStatus::class, names = ["NEW"], mode = EnumSource.Mode.EXCLUDE)
    fun `should throw exception when starting rental with invalid status`(status: RentalStatus) {
        // given
        val currentDate = OffsetDateTime.now(ZoneOffset.UTC)
        val rental = rental(status = status)

        // when
        val result = catchThrowable { rental.start(currentDate) }

        // then
        assertThat(result).isInstanceOf(IllegalStateException::class.java)
            .hasMessage("Rental cannot be started because it is not in status NEW")
    }

    @Test
    fun `should end rental`() {
        // given
        val currentDate = OffsetDateTime.now(ZoneOffset.UTC)
        val rental = rental(status = RentalStatus.STARTED)

        // when
        rental.end(currentDate)

        // then
        assertThat(rental.status).isEqualTo(RentalStatus.ENDED)
    }

    @Test
    fun `should throw exception when ending rental before its end date`() {
        // given
        val currentDate = OffsetDateTime.now(ZoneOffset.UTC)
        val rental = rental(status = RentalStatus.STARTED, endDate = OffsetDateTime.now(ZoneOffset.UTC).plusDays(1))

        // when
        val result = catchThrowable { rental.end(currentDate) }

        // then
        assertThat(result).isInstanceOf(IllegalStateException::class.java)
            .hasMessage("Rental cannot be ended when current date is before end date")
    }

    @ParameterizedTest
    @EnumSource(value = RentalStatus::class, names = ["STARTED"], mode = EnumSource.Mode.EXCLUDE)
    fun `should throw exception when ending rental with invalid status`(status: RentalStatus) {
        // given
        val currentDate = OffsetDateTime.now(ZoneOffset.UTC)
        val rental = rental(status = status)

        // when
        val result = catchThrowable { rental.end(currentDate) }

        // then
        assertThat(result).isInstanceOf(IllegalStateException::class.java)
            .hasMessage("Rental cannot be ended because it is not in status STARTED")
    }
}