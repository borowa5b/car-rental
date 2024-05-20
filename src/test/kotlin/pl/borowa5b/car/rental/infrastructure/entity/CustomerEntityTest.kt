package pl.borowa5b.car.rental.infrastructure.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.borowa5b.car.rental.domain.model.DomainObjects.customer
import pl.borowa5b.car.rental.infrastructure.entity.EntityObjects.customerEntity

class CustomerEntityTest {

    @Test
    fun `should convert from domain object`() {
        // given
        val customer = customer()

        // when
        val result = CustomerEntity.fromDomain(customer)

        // then
        assertThat(result.id).isEqualTo(customer.id.value)
        assertThat(result.name).isEqualTo(customer.name)
        assertThat(result.surname).isEqualTo(customer.surname)
        assertThat(result.email).isEqualTo(customer.email)
        assertThat(result.phoneNumber).isEqualTo(customer.phoneNumber)
        assertThat(result.address).isEqualTo(customer.address)
        assertThat(result.documentNumber).isEqualTo(customer.documentNumber)
        assertThat(result.entityVersion).isEqualTo(customer.version)
        assertThat(result.creationDate).isNotNull()
        assertThat(result.modificationDate).isNotNull()
    }

    @Test
    fun `should convert to domain object`() {
        // given
        val customerEntity = customerEntity()

        // when
        val result = customerEntity.toDomain()

        // then
        assertThat(result.id.value).isEqualTo(customerEntity.id)
        assertThat(result.name).isEqualTo(customerEntity.name)
        assertThat(result.surname).isEqualTo(customerEntity.surname)
        assertThat(result.email).isEqualTo(customerEntity.email)
        assertThat(result.phoneNumber).isEqualTo(customerEntity.phoneNumber)
        assertThat(result.address).isEqualTo(customerEntity.address)
        assertThat(result.documentNumber).isEqualTo(customerEntity.documentNumber)
        assertThat(result.version).isEqualTo(customerEntity.entityVersion)
    }
}