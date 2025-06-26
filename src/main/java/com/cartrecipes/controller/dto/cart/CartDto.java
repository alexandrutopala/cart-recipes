package com.cartrecipes.controller.dto.cart;

import com.cartrecipes.controller.dto.product.ProductDto;
import com.cartrecipes.controller.dto.recipe.ListingRecipeDto;

import java.util.List;

public record CartDto(Long id,
                      Integer totalInCents,
                      List<ProductDto> products,
                      List<ListingRecipeDto> recipes) {
}
