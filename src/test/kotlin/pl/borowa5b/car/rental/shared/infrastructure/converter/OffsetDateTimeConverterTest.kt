package pl.borowa5b.car.rental.shared.infrastructure.converter

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.shared.infrastructure.converter.OffsetDateTimeConverter
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

class OffsetDateTimeConverterTest {

    @Test
    fun `should convert from offset date time to local date time`() {
        // given
        val offsetDateTime = OffsetDateTime.now(ZoneOffset.UTC)
        val expectedLocalDateTime = offsetDateTime.truncatedTo(ChronoUnit.MICROS).toLocalDateTime()
        val converter = OffsetDateTimeConverter()

        // when
        val result = converter.convertToDatabaseColumn(offsetDateTime)

        // then
        assertThat(result).isEqualTo(expectedLocalDateTime)
    }

    @Test
    fun `should convert from offset date time to local date time when offset date time is null`() {
        // given
        val offsetDateTime = null
        val converter = OffsetDateTimeConverter()

        // when
        val result = converter.convertToDatabaseColumn(offsetDateTime)

        // then
        assertThat(result).isNull()
    }

    @Test
    fun `should convert from local date time to offset date time`() {
        // given
        val localDateTime = LocalDateTime.now()
        val expectedOffsetDateTime = localDateTime.atOffset(ZoneOffset.UTC)
        val converter = OffsetDateTimeConverter()

        // when
        val result = converter.convertToEntityAttribute(localDateTime)

        // then
        assertThat(result).isEqualTo(expectedOffsetDateTime)
    }

    @Test
    fun `should convert from local date time to offset date time when local date time is null`() {
        // given
        val localDateTime = null
        val converter = OffsetDateTimeConverter()

        // when
        val result = converter.convertToEntityAttribute(localDateTime)

        // then
        assertThat(result).isNull()
    }
}