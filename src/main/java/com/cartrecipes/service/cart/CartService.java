package com.cartrecipes.service.cart;

import com.cartrecipes.exception.DuplicateRecipeException;
import com.cartrecipes.exception.NotFoundException;
import com.cartrecipes.model.Cart;
import com.cartrecipes.model.Recipe;
import com.cartrecipes.service.recipe.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;

    private final RecipeService recipeService;

    @Transactional(readOnly = true)
    public Cart findWithRecipesAndProductsById(Long id) {
        return cartRepository.findWithRecipesAndProductsById(id)
                .orElseThrow(() -> new NotFoundException("Cart not found with id: " + id));
    }

    public Cart addRecipes(Long cartId, Set<Long> recipeIds) {
        var recipes = recipeService.findByIds(recipeIds);

        if (recipeIds.size() != recipes.size()) {
            var foundRecipeIds = recipes.stream()
                    .map(Recipe::getId)
                    .collect(Collectors.toSet());
            var missingRecipeIds = new ArrayList<>(recipeIds);
            missingRecipeIds.removeIf(foundRecipeIds::contains);
            throw new NotFoundException("One or more recipes not found: " + missingRecipeIds);
        }

        var cart = cartRepository.findWithRecipesAndProductsById(cartId)
                .orElseThrow(() -> new NotFoundException("Cart not found with id: " + cartId));

        var duplicateRecipeIds = recipes.stream()
                .filter(recipe -> !cart.getRecipes().add(recipe))
                .map(Recipe::getId)
                .collect(Collectors.toSet());

        if (!duplicateRecipeIds.isEmpty()) {
            throw new DuplicateRecipeException(duplicateRecipeIds);
        }

        cart.setTotalInCents(cart.getTotalInCents() + recipes.stream()
                .mapToInt(Recipe::getPriceInCents)
                .sum());

        return cartRepository.save(cart);
    }

    public Cart removeRecipe(Long cartId, Long recipeId) {
        var cart = cartRepository.findWithRecipesAndProductsById(cartId)
                .orElseThrow(() -> new NotFoundException("Cart not found with id: " + cartId));

        var recipe = recipeService.findByIds(Set.of(recipeId)).stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Recipe not found with id: " + recipeId));

        if (!cart.getRecipes().remove(recipe)) {
            throw new NotFoundException("Recipe not found in the cart");
        }

        cart.setTotalInCents(cart.getTotalInCents() - recipe.getPriceInCents());

        return cartRepository.save(cart);
    }
}
