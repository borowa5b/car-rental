package pl.borowa5b.car.rental.shared.domain.dictionaries

import pl.borowa5b.car.rental.cars.domain.vo.Brand
import pl.borowa5b.car.rental.events.domain.vo.ApplicationEventStatus
import pl.borowa5b.car.rental.events.domain.vo.ExternalEventStatus
import pl.borowa5b.car.rental.rentals.domain.vo.RentalStatus

object DictionariesResolver {

    fun resolve(): Map<String, Array<*>> {
        return mapOf(
            "brand" to Brand.entries.toTypedArray(),
            "applicationEventStatus" to ApplicationEventStatus.entries.toTypedArray(),
            "externalEventStatus" to ExternalEventStatus.entries.toTypedArray(),
            "rentalStatus" to RentalStatus.entries.toTypedArray()
        )
    }
}