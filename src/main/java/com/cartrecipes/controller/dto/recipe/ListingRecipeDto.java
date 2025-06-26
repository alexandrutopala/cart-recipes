package com.cartrecipes.controller.dto.recipe;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ListingRecipeDto {
    private Long id;
    private String name;
    private Integer priceInCents;
}
