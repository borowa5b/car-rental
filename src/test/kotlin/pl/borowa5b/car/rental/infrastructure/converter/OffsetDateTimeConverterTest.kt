package pl.borowa5b.car.rental.infrastructure.converter

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

class OffsetDateTimeConverterTest {

    @Test
    fun `should convert from offset date time to local date time`() {
        // given
        val offsetDateTime = OffsetDateTime.now(ZoneOffset.UTC)
        val converter = OffsetDateTimeConverter()
        val expectedLocalDateTime = offsetDateTime.truncatedTo(ChronoUnit.MICROS).toLocalDateTime()

        // when
        val result = converter.convertToDatabaseColumn(offsetDateTime)

        // then
        assertThat(result).isEqualTo(expectedLocalDateTime)
    }

    @Test
    fun `should convert from local date time to offset date time`() {
        // given
        val localDateTime = LocalDateTime.now()
        val converter = OffsetDateTimeConverter()
        val expectedOffsetDateTime = localDateTime.atOffset(ZoneOffset.UTC)

        // when
        val result = converter.convertToEntityAttribute(localDateTime)

        // then
        assertThat(result).isEqualTo(expectedOffsetDateTime)
    }
}