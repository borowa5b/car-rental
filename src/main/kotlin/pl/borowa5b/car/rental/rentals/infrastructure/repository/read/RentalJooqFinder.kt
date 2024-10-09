package pl.borowa5b.car.rental.rentals.infrastructure.repository.read

import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.impl.DSL.field
import org.jooq.impl.SQLDataType
import org.springframework.stereotype.Repository
import pl.borowa5b.car.rental.rentals.domain.exception.RentalNotFoundException
import pl.borowa5b.car.rental.rentals.domain.repository.read.RentalConditionsBuilder
import pl.borowa5b.car.rental.rentals.domain.repository.read.RentalDetails
import pl.borowa5b.car.rental.rentals.domain.repository.read.RentalFinder
import pl.borowa5b.car.rental.rentals.domain.repository.read.RentalQuery
import pl.borowa5b.car.rental.rentals.domain.vo.RentalId
import pl.borowa5b.car.rental.rentals.infrastructure.repository.read.RentalTableDefinition.Column
import pl.borowa5b.car.rental.rentals.infrastructure.repository.read.RentalTableDefinition.TABLE_NAME
import pl.borowa5b.car.rental.shared.domain.pagination.Page
import pl.borowa5b.car.rental.shared.domain.pagination.Pageable

@Repository
class RentalJooqFinder(private val dslContext: DSLContext) : RentalFinder {

    override fun findBy(query: RentalQuery, page: Page): Pageable<RentalDetails> {
        val build = RentalConditionsBuilder.build(query)
        val data = dslContext
            .select(allFields())
            .from(TABLE_NAME)
            .where(build)
            .orderBy(field(page.getOrderAsString()))
            .limit((page.number - 1) * page.size, page.size + 1)
            .fetch()
            .into(RentalDetails::class.java)
        return Pageable.of(data, page)
    }

    override fun findBy(rentalId: RentalId): RentalDetails =
        dslContext
            .select(allFields())
            .from(TABLE_NAME)
            .where(field(Column.ID).eq(rentalId.value))
            .fetchOne()
            ?.into(RentalDetails::class.java) ?: throw RentalNotFoundException(rentalId)

    fun allFields(): List<Field<*>> = listOf(
        field(Column.ID, SQLDataType.VARCHAR),
        field(Column.CAR_ID, SQLDataType.VARCHAR),
        field(Column.CUSTOMER_ID, SQLDataType.VARCHAR),
        field(Column.STATUS, SQLDataType.VARCHAR),
        field(Column.PRICE, SQLDataType.DECIMAL),
        field(Column.START_DATE, SQLDataType.OFFSETDATETIME),
        field(Column.END_DATE, SQLDataType.OFFSETDATETIME),
        field(Column.CREATION_DATE, SQLDataType.OFFSETDATETIME),
        field(Column.MODIFICATION_DATE, SQLDataType.OFFSETDATETIME),
        field(Column.ENTITY_VERSION, SQLDataType.INTEGER)
    )
}