package com.cartrecipes.controller.dto.recipe;

import com.cartrecipes.controller.dto.product.ProductDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DetailedRecipeDto {
    private Long id;
    private String name;
    private Integer priceInCents;
    private List<ProductDto> products;
}
