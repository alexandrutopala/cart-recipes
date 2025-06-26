package com.cartrecipes.service.recipe;

import com.cartrecipes.model.Recipe;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @EntityGraph(attributePaths = {"products"})
    Optional<Recipe> findWithProductsById(Long id);
}
