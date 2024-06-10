package pl.borowa5b.car.rental.rentals.domain.repository.read

import org.jooq.Condition
import org.jooq.impl.DSL.field
import org.jooq.impl.DSL.noCondition
import pl.borowa5b.car.rental.rentals.infrastructure.repository.read.RentalTableDefinition.Column

object RentalConditionsBuilder {

    fun build(query: RentalQuery): Condition {
        val conditions = mutableListOf<Condition>()

        with(query) {
            carId?.let {
                conditions.add(field(Column.CAR_ID).eq(it.value))
            }
            customerId?.let {
                conditions.add(field(Column.CUSTOMER_ID).eq(it.value))
            }
            startDateFrom?.let {
                conditions.add(field(Column.START_DATE).greaterOrEqual(it))
            }
            startDateTo?.let {
                conditions.add(field(Column.START_DATE).lessOrEqual(it))
            }
            endDateFrom?.let {
                conditions.add(field(Column.END_DATE).greaterOrEqual(it))
            }
            endDateTo?.let {
                conditions.add(field(Column.END_DATE).lessOrEqual(it))
            }
        }

        return conditions.and()
    }

    private fun List<Condition>.and() = fold(noCondition()) { acc, condition -> condition.and(acc) }
}