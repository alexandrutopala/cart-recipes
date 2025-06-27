package com.cartrecipes.model

import jakarta.persistence.*
import jakarta.validation.constraints.Min

@Entity
@Table(name = "recipes")
class Recipe (
    @field:Id
    val id: Long,

    @field:Column(name = "name", nullable = false)
    val name: String,

    @field:Column(name = "price_in_cents", nullable = false)
    @field:Min(0)
    var priceInCents: Int,

    @field:ManyToMany
    @field:JoinTable(
        name = "recipe_items",
        joinColumns = [JoinColumn(name = "recipe_id", referencedColumnName = "id", nullable = false)],
        inverseJoinColumns = [JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)],
        uniqueConstraints = [UniqueConstraint(columnNames = ["recipe_id", "product_id"], name = "uk_recipe_items")]
    )
    val products: MutableSet<Product> = mutableSetOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Recipe

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}