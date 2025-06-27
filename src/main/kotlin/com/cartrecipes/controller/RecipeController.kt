package com.cartrecipes.controller

import com.cartrecipes.controller.dto.recipe.DetailedRecipeDto
import com.cartrecipes.controller.dto.recipe.ListingRecipeDto
import com.cartrecipes.service.recipe.RecipeService
import org.modelmapper.ModelMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(RecipeController.BASE_PATH)
class RecipeController (
    private val modelMapper: ModelMapper,
    private val recipeService: RecipeService
) {
    companion object {
        const val BASE_PATH = "/recipes"
    }

    @GetMapping
    fun findAll(@PageableDefault pageable: Pageable): Page<ListingRecipeDto> {
        return recipeService.findAll(pageable)
            .map { modelMapper.map(it, ListingRecipeDto::class.java) }
    }

    @GetMapping("/{recipeId}")
    fun findById(@PathVariable recipeId: Long): DetailedRecipeDto {
        return recipeService.findById(recipeId)
            .let { modelMapper.map(it, DetailedRecipeDto::class.java) }
    }
}