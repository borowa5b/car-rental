package pl.borowa5b.car.rental.cars.infrastructure.repository.read

import org.jooq.Condition
import org.jooq.impl.DSL.field
import org.jooq.impl.DSL.noCondition
import pl.borowa5b.car.rental.cars.domain.repository.read.CarQuery
import pl.borowa5b.car.rental.cars.infrastructure.repository.read.CarTableDefinition.Column

object CarConditionsBuilder {

    fun build(query: CarQuery): Condition {
        val conditions = mutableListOf<Condition>()

        with(query) {
            id?.let {
                conditions.add(field(Column.ID).eq(it.value))
            }
            brand?.let {
                conditions.add(field(Column.BRAND).eq(it.name))
            }
            model?.let {
                conditions.add(field(Column.MODEL).eq(it))
            }
            generation?.let {
                conditions.add(field(Column.GENERATION).eq(it))
            }
            productionYear?.let {
                conditions.add(field(Column.PRODUCTION_YEAR).eq(it))
            }
            color?.let {
                conditions.add(field(Column.COLOR).eq(it))
            }
            pricePerDayFrom?.let {
                conditions.add(field(Column.PRICE_PER_DAY).greaterOrEqual(it))
            }
            pricePerDayTo?.let {
                conditions.add(field(Column.PRICE_PER_DAY).lessOrEqual(it))
            }
            quantityFrom?.let {
                conditions.add(field(Column.QUANTITY).greaterOrEqual(it))
            }
            quantityTo?.let {
                conditions.add(field(Column.QUANTITY).lessOrEqual(it))
            }
        }

        return conditions.and()
    }

    private fun List<Condition>.and() = fold(noCondition()) { acc, condition -> condition.and(acc) }
}