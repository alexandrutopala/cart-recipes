package com.cartrecipes.controller.dto.recipe;

import com.cartrecipes.controller.dto.product.ProductDto;

import java.util.List;

public record DetailedRecipeDto(Long id,
                                String name,
                                Integer priceInCents,
                                List<ProductDto> products)  {
}
