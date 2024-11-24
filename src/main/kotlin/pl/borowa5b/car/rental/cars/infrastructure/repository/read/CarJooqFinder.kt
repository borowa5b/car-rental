package pl.borowa5b.car.rental.cars.infrastructure.repository.read

import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.impl.DSL.field
import org.jooq.impl.SQLDataType
import org.springframework.stereotype.Repository
import pl.borowa5b.car.rental.cars.domain.repository.read.CarDetails
import pl.borowa5b.car.rental.cars.domain.repository.read.CarFinder
import pl.borowa5b.car.rental.cars.domain.repository.read.CarQuery
import pl.borowa5b.car.rental.cars.domain.shared.exception.CarNotFoundException
import pl.borowa5b.car.rental.cars.domain.shared.vo.CarId
import pl.borowa5b.car.rental.cars.infrastructure.repository.read.CarTableDefinition.Column
import pl.borowa5b.car.rental.cars.infrastructure.repository.read.CarTableDefinition.TABLE_NAME
import pl.borowa5b.car.rental.shared.domain.pagination.Page
import pl.borowa5b.car.rental.shared.domain.pagination.Pageable

@Repository
class CarJooqFinder(private val dslContext: DSLContext) : CarFinder {

    override fun findBy(query: CarQuery, page: Page): Pageable<CarDetails> {
        val build = CarConditionsBuilder.build(query)
        val data = dslContext
            .select(allFields())
            .from(TABLE_NAME)
            .where(build)
            .orderBy(field(page.getOrderAsString()))
            .limit((page.number - 1) * page.size, page.size + 1)
            .fetch()
            .into(CarDetails::class.java)
        return Pageable.of(data, page)
    }

    override fun findBy(carId: CarId): CarDetails =
        dslContext
            .select(allFields())
            .from(TABLE_NAME)
            .where(field(Column.ID).eq(carId.value))
            .fetchOne()
            ?.into(CarDetails::class.java) ?: throw CarNotFoundException(carId)

    fun allFields(): List<Field<*>> = listOf(
        field(Column.ID, SQLDataType.VARCHAR),
        field(Column.BRAND, SQLDataType.VARCHAR),
        field(Column.MODEL, SQLDataType.VARCHAR),
        field(Column.GENERATION, SQLDataType.VARCHAR),
        field(Column.PRODUCTION_YEAR, SQLDataType.INTEGER),
        field(Column.COLOR, SQLDataType.VARCHAR),
        field(Column.PRICE_PER_DAY, SQLDataType.DECIMAL),
        field(Column.QUANTITY, SQLDataType.INTEGER),
        field(Column.CREATION_DATE, SQLDataType.OFFSETDATETIME),
        field(Column.MODIFICATION_DATE, SQLDataType.OFFSETDATETIME),
        field(Column.ENTITY_VERSION, SQLDataType.INTEGER)
    )
}