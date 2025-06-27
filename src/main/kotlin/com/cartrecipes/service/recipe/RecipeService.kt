package com.cartrecipes.service.recipe

import com.cartrecipes.exception.NotFoundException
import com.cartrecipes.model.Recipe
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RecipeService internal constructor (
    private val recipeRepository: RecipeRepository
) {
    @Transactional(readOnly = true)
    fun findAll(pageable: Pageable): Page<Recipe> {
        return recipeRepository.findAll(pageable)
    }

    @Transactional(readOnly = true)
    fun findByIds(ids: Collection<Long>): Collection<Recipe> {
        return recipeRepository.findAllById(ids)
    }

    @Transactional(readOnly = true)
    fun findWithProductsById(id: Long): Recipe {
        return recipeRepository.findWithProductsById(id) ?: throw NotFoundException("Recipe not found with id: $id")
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): Recipe {
        return recipeRepository.findById(id).orElseThrow { NotFoundException("Recipe not found with id: $id") }
    }
}