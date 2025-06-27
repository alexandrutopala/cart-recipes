package com.cartrecipes.service.recipe

import com.cartrecipes.model.Recipe
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

internal interface RecipeRepository: JpaRepository<Recipe, Long> {
    @EntityGraph(attributePaths = ["products"])
    fun findWithProductsById(id: Long): Recipe?
}