package pl.borowa5b.car.rental.customers.infrastructure.repository.read

import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.impl.DSL.field
import org.jooq.impl.SQLDataType
import org.springframework.stereotype.Repository
import pl.borowa5b.car.rental.customers.domain.repository.read.CustomerDetails
import pl.borowa5b.car.rental.customers.domain.repository.read.CustomerFinder
import pl.borowa5b.car.rental.customers.domain.repository.read.CustomerQuery
import pl.borowa5b.car.rental.customers.domain.shared.exception.CustomerNotFoundException
import pl.borowa5b.car.rental.customers.domain.shared.vo.CustomerId
import pl.borowa5b.car.rental.customers.infrastructure.repository.read.CustomerTableDefinition.Column
import pl.borowa5b.car.rental.customers.infrastructure.repository.read.CustomerTableDefinition.TABLE_NAME
import pl.borowa5b.car.rental.shared.domain.pagination.Page
import pl.borowa5b.car.rental.shared.domain.pagination.Pageable

@Repository
class CustomerJooqFinder(private val dslContext: DSLContext) : CustomerFinder {

    override fun findBy(
        query: CustomerQuery,
        page: Page
    ): Pageable<CustomerDetails> {
        val build = CustomerConditionsBuilder.build(query)
        val data = dslContext
            .select(allFields())
            .from(TABLE_NAME)
            .where(build)
            .orderBy(field(page.getOrderAsString()))
            .limit((page.number - 1) * page.size, page.size + 1)
            .fetch()
            .into(CustomerDetails::class.java)

        return Pageable.of(data, page)
    }

    override fun findBy(customerId: CustomerId): CustomerDetails =
        dslContext
            .select(allFields())
            .from(TABLE_NAME)
            .where(field(Column.ID).eq(customerId.value))
            .fetchOne()
            ?.into(CustomerDetails::class.java) ?: throw CustomerNotFoundException(customerId)

    fun allFields(): List<Field<*>> = listOf(
        field(Column.ID, SQLDataType.VARCHAR),
        field(Column.NAME, SQLDataType.VARCHAR),
        field(Column.SURNAME, SQLDataType.VARCHAR),
        field(Column.EMAIL, SQLDataType.VARCHAR),
        field(Column.PHONE_NUMBER, SQLDataType.VARCHAR),
        field(Column.ADDRESS, SQLDataType.VARCHAR),
        field(Column.DOCUMENT_NUMBER, SQLDataType.VARCHAR),
        field(Column.CREATION_DATE, SQLDataType.OFFSETDATETIME),
        field(Column.MODIFICATION_DATE, SQLDataType.OFFSETDATETIME),
        field(Column.ENTITY_VERSION, SQLDataType.INTEGER)
    )
}