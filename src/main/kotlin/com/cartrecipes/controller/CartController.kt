package com.cartrecipes.controller

import com.cartrecipes.controller.dto.cart.CartDto
import com.cartrecipes.controller.dto.recipe.AddRecipesToCartDto
import com.cartrecipes.service.cart.CartService
import jakarta.validation.Valid
import org.modelmapper.ModelMapper
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(CartController.BASE_PATH)
@Validated
class CartController (
    private val cartService: CartService,
    private val modelMapper: ModelMapper
) {
    companion object {
        const val BASE_PATH = "/carts"
    }

    @GetMapping("/{cartId}")
    fun findById(@PathVariable cartId: Long): CartDto {
        return cartService.findWithRecipesAndProductsById(cartId)
            .let { modelMapper.map(it, CartDto::class.java) }
    }

    @PostMapping("/{cartId}/add_recipe")
    fun addRecipes(
        @PathVariable cartId: Long,
        @RequestBody @Valid addRecipesToCartDto: AddRecipesToCartDto
    ): CartDto {
        return cartService.addRecipes(cartId, addRecipesToCartDto.recipeIds)
            .let { modelMapper.map(it, CartDto::class.java) }
    }

    @DeleteMapping("/{cartId}/recipes/{recipeId}")
    fun removeRecipe(
        @PathVariable cartId: Long,
        @PathVariable recipeId: Long
    ): CartDto {
        return cartService.removeRecipe(cartId, recipeId)
            .let { modelMapper.map(it, CartDto::class.java) }
    }
}