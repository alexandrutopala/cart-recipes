package com.cartrecipes.controller.dto.cart

import com.cartrecipes.controller.dto.product.ProductDto
import com.cartrecipes.controller.dto.recipe.ListingRecipeDto

class CartDto {
    var id: Long = 0
    var totalInCents: Int = 0
    var products: List<ProductDto> = emptyList()
    var recipes: List<ListingRecipeDto> = emptyList()
}