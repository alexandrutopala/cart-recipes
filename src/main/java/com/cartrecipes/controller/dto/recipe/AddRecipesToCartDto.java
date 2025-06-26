package com.cartrecipes.controller.dto.recipe;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record AddRecipesToCartDto(
        @Size(min = 1, max = 5, message = "You can add between 1 and 5 recipes to the cart at a time.")
        @NotNull
        Set<Long> recipeIds
) { }
