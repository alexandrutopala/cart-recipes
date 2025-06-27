package com.cartrecipes.service.cart

import com.cartrecipes.exception.DuplicateRecipeException
import com.cartrecipes.exception.NotFoundException
import com.cartrecipes.model.Cart
import com.cartrecipes.service.recipe.RecipeService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CartService internal constructor(
    private val cartRepository: CartRepository,
    private val recipeService: RecipeService
) {

    fun addRecipes(cartId: Long, recipeIds: Collection<Long>): Cart {
        val recipes = recipeService.findByIds(recipeIds)

        if (recipes.size != recipeIds.size) {
            val foundRecipeIds = recipes.map { it.id }.toSet()
            val missingRecipeIds = recipeIds.filterNot { it in foundRecipeIds }
            throw NotFoundException("One or more recipes not found: $missingRecipeIds")
        }

        val cart = cartRepository.findWithRecipesAndProductsById(cartId) ?: throw NotFoundException("Cart not found with id: $cartId")

        val duplicateRecipeIds = recipes
            .filterNot { cart.recipes.add(it) }
            .map { it.id }

        if (duplicateRecipeIds.isNotEmpty()) {
            throw DuplicateRecipeException(duplicateRecipeIds)
        }

        cart.totalInCents += recipes.sumOf { it.priceInCents }

        return cartRepository.save(cart)
    }

    fun removeRecipe(cartId: Long, recipeId: Long): Cart {
        val cart = cartRepository.findWithRecipesAndProductsById(cartId) ?: throw NotFoundException("Cart not found with id: $cartId")
        val recipe = recipeService.findById(recipeId)

        if (!cart.recipes.remove(recipe)) {
            throw NotFoundException("Recipe not found in the cart")
        }

        cart.totalInCents -= recipe.priceInCents

        return cartRepository.save(cart)
    }

    fun findWithRecipesAndProductsById(cartId: Long): Cart {
        return cartRepository.findWithRecipesAndProductsById(cartId)
            ?: throw NotFoundException("Cart not found with id: $cartId")
    }
}