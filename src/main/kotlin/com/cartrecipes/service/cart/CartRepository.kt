package com.cartrecipes.service.cart

import com.cartrecipes.model.Cart
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

internal interface CartRepository: JpaRepository<Cart, Long> {
    @EntityGraph(attributePaths = ["recipes", "products"])
    fun findWithRecipesAndProductsById(id: Long): Cart?
}