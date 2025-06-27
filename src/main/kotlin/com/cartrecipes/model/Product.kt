package com.cartrecipes.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Min

@Entity
@Table(name = "products")
class Product (
    @field:Id
    val id: Long,

    @field:Column(name = "name", nullable = false)
    val name: String,

    @field:Column(name = "price_in_cents", nullable = false)
    @field:Min(0)
    var priceInCents: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}