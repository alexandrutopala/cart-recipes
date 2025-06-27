package com.cartrecipes.controller.dto.recipe

import com.cartrecipes.controller.dto.product.ProductDto

class DetailedRecipeDto {
    var id: Long = 0
    var name: String? = null
    var priceInCents: Int = 0
    var products: List<ProductDto> = emptyList()
}