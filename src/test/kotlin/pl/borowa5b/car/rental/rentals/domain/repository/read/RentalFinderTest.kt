package pl.borowa5b.car.rental.rentals.domain.repository.read

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.customers.domain.shared.vo.ValueObjects.customerId
import pl.borowa5b.car.rental.rentals.domain.repository.read.ReadObjects.rentalDetails
import pl.borowa5b.car.rental.rentals.domain.repository.read.ReadObjects.rentalQuery
import pl.borowa5b.car.rental.rentals.domain.vo.RentalId
import pl.borowa5b.car.rental.shared.domain.pagination.Page
import pl.borowa5b.car.rental.shared.domain.pagination.Pageable
import pl.borowa5b.car.rental.shared.domain.pagination.PaginationObjects.page

class RentalFinderTest {

    private val rentalFinder: RentalFinder = TestRentalFinder()

    @Test
    fun `should find rentals by query`() {
        // given
        val customerId = customerId()
        val rentalDetails1 = rentalDetails(customerId = customerId)
        val rentalDetails2 = rentalDetails(customerId = customerId)
        val rentalDetails3 = rentalDetails(customerId = customerId())
        (rentalFinder as TestRentalFinder).save(rentalDetails1, rentalDetails2, rentalDetails3)
        val query = rentalQuery(customerId = customerId)

        // when
        val result = rentalFinder.findBy(query, page())

        // then
        val data = result.data
        assertThat(data).hasSize(2)
        assertThat(data[0]).isEqualTo(rentalDetails1)
        assertThat(data[1]).isEqualTo(rentalDetails2)
    }

    @Test
    fun `should find rental by id`() {
        // given
        val rentalId = RentalId("RNL123")
        val rentalDetails = rentalDetails(id = rentalId)
        (rentalFinder as TestRentalFinder).save(rentalDetails)

        // when
        val result = rentalFinder.findBy(rentalId)

        // then
        assertThat(result).isEqualTo(rentalDetails)
    }

    private class TestRentalFinder(private val rentals: ArrayList<RentalDetails> = ArrayList()) : RentalFinder {

        override fun findBy(query: RentalQuery, page: Page): Pageable<RentalDetails> =
            Pageable.of(rentals.filter { rental ->
                var condition = true
                query.id?.let {
                    condition = condition && it.value == rental.id
                }
                query.carId?.let {
                    condition = condition && it.value == rental.carId
                }
                query.status?.let {
                    condition = condition && it.name == rental.status
                }
                query.customerId?.let {
                    condition = condition && it.value == rental.customerId
                }
                query.startDateFrom?.let {
                    condition = condition && it.isAfter(rental.startDate)
                }
                query.startDateTo?.let {
                    condition = condition && it.isBefore(rental.startDate)
                }
                query.endDateFrom?.let {
                    condition = condition && it.isAfter(rental.endDate)
                }
                query.endDateTo?.let {
                    condition = condition && it.isBefore(rental.endDate)
                }
                condition
            }.toList(), page)

        override fun findBy(rentalId: RentalId): RentalDetails = rentals.first { it.id == rentalId.value }

        fun save(vararg rental: RentalDetails) = rental.forEach { rentals.add(it) }
    }
}