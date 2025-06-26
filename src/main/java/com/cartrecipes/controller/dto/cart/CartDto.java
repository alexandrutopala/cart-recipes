package com.cartrecipes.controller.dto.cart;

import com.cartrecipes.controller.dto.product.ProductDto;
import com.cartrecipes.controller.dto.recipe.ListingRecipeDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CartDto {
    private Long id;
    private Integer totalInCents;
    private List<ProductDto> products;
    private List<ListingRecipeDto> recipes;
}
