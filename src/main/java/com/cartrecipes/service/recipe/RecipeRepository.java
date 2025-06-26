package com.cartrecipes.service.recipe;

import com.cartrecipes.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
