package com.cartrecipes.model

import jakarta.persistence.*
import jakarta.validation.constraints.Min

@Entity
@Table(name = "carts")
class Cart (
    @field:Id
    val id: Long,

    @field:Column(name = "total_in_cents", nullable = false)
    @field:Min(0)
    var totalInCents: Int,

    @field:ManyToMany
    @field:JoinTable(
        name = "cart_items",
        joinColumns = [JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)],
        inverseJoinColumns = [JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)],
        uniqueConstraints = [UniqueConstraint(columnNames = ["cart_id", "product_id"], name = "uk_cart_items")]
    )
    val products: MutableSet<Product> = mutableSetOf(),

    @field:ManyToMany
    @field:JoinTable(
        name = "cart_recipes",
        joinColumns = [JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)],
        inverseJoinColumns = [JoinColumn(name = "recipe_id", referencedColumnName = "id", nullable = false)],
        uniqueConstraints = [UniqueConstraint(columnNames = ["cart_id", "recipe_id"], name = "uk_cart_recipes")]
    )
    val recipes: MutableSet<Recipe> = mutableSetOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cart

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}