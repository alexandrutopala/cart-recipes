package com.cartrecipes.controller;

import com.cartrecipes.controller.dto.recipe.DetailedRecipeDto;
import com.cartrecipes.controller.dto.recipe.ListingRecipeDto;
import com.cartrecipes.service.recipe.RecipeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RecipeController.BASE_PATH)
@RequiredArgsConstructor
public class RecipeController {
    public static final String BASE_PATH = "/recipes";

    private final ModelMapper modelMapper;

    private final RecipeService recipeService;

    @GetMapping
    public Page<ListingRecipeDto> findAll(@PageableDefault Pageable pageable) {
        var recipes = recipeService.findAll(pageable);
        return recipes.map(recipe -> modelMapper.map(recipe, ListingRecipeDto.class));
    }

    @GetMapping("/{recipeId}")
    public DetailedRecipeDto findById(@PathVariable Long recipeId) {
        var recipe = recipeService.findWithProductsById(recipeId);
        return modelMapper.map(recipe, DetailedRecipeDto.class);
    }
}
