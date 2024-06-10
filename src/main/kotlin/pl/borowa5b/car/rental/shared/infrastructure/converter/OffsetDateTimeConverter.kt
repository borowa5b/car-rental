package pl.borowa5b.car.rental.shared.infrastructure.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit

@Converter(autoApply = true)
class OffsetDateTimeConverter : AttributeConverter<OffsetDateTime?, LocalDateTime?> {

    override fun convertToDatabaseColumn(offsetDateTime: OffsetDateTime?): LocalDateTime? =
        offsetDateTime?.truncatedTo(ChronoUnit.MICROS)?.toLocalDateTime()

    override fun convertToEntityAttribute(localDateTime: LocalDateTime?): OffsetDateTime? =
        localDateTime?.atOffset(ZoneOffset.UTC)
}