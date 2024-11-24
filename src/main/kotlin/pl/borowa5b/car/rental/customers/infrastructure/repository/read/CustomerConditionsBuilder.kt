package pl.borowa5b.car.rental.customers.infrastructure.repository.read

import org.jooq.Condition
import org.jooq.impl.DSL.field
import org.jooq.impl.DSL.noCondition
import pl.borowa5b.car.rental.customers.domain.repository.read.CustomerQuery

object CustomerConditionsBuilder {

    fun build(query: CustomerQuery): Condition {
        val conditions = mutableListOf<Condition>()

        with(query) {
            id?.let {
                conditions.add(field(CustomerTableDefinition.Column.ID).eq(it.value))
            }
            name?.let {
                conditions.add(field(CustomerTableDefinition.Column.NAME).eq(it))
            }
            surname?.let {
                conditions.add(field(CustomerTableDefinition.Column.SURNAME).eq(it))
            }
            email?.let {
                conditions.add(field(CustomerTableDefinition.Column.EMAIL).eq(it))
            }
            phoneNumber?.let {
                conditions.add(field(CustomerTableDefinition.Column.PHONE_NUMBER).eq(it))
            }
        }

        return conditions.and()
    }

    private fun List<Condition>.and() = fold(noCondition()) { acc, condition -> condition.and(acc) }
}