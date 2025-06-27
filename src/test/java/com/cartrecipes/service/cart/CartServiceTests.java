package com.cartrecipes.service.cart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.cartrecipes.exception.DuplicateRecipeException;
import com.cartrecipes.exception.NotFoundException;
import com.cartrecipes.model.Cart;
import com.cartrecipes.model.Recipe;
import com.cartrecipes.service.recipe.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartServiceTests {

    @Mock
    private RecipeService recipeService;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    private Cart testCart;
    private Recipe recipe1;
    private Recipe recipe2;

    @BeforeEach
    void setUp() {
        recipe1 = new Recipe(1L, "Recipe 1", 1000, new HashSet<>());

        recipe2 = new Recipe(2L, "Recipe 2", 1500, new HashSet<>());

        testCart = new Cart(1L, 0, new HashSet<>(), new HashSet<>());
    }

    @Test
    void GIVEN_cart_and_recipe_WHEN_add_recipes_THEN_recipe_added_and_total_updated() {
        when(recipeService.findByIds(anySet())).thenReturn(Set.of(recipe1));
        when(cartRepository.findWithRecipesAndProductsById(1L)).thenReturn(testCart);
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cart result = cartService.addRecipes(1L, Set.of(1L));

        assertThat(result.getRecipes()).hasSize(1).contains(recipe1);
        assertThat(result.getTotalInCents()).isEqualTo(1000);
    }

    @Test
    void GIVEN_cart_and_multiple_recipes_WHEN_add_recipes_THEN_recipes_added_and_total_updated() {
        when(recipeService.findByIds(anySet())).thenReturn(Set.of(recipe1, recipe2));
        when(cartRepository.findWithRecipesAndProductsById(1L)).thenReturn(testCart);
        when(cartRepository.save(any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cart result = cartService.addRecipes(1L, Set.of(1L, 2L));

        assertThat(result.getRecipes()).hasSize(2).contains(recipe1, recipe2);
        assertThat(result.getTotalInCents()).isEqualTo(2500);
    }

    @Test
    void GIVEN_non_existing_cart_WHEN_add_recipes_THEN_not_found() {
        when(recipeService.findByIds(anySet())).thenReturn(Set.of(recipe1));
        when(cartRepository.findWithRecipesAndProductsById(99L)).thenReturn(null);

        assertThatThrownBy(() -> cartService.addRecipes(99L, Set.of(1L)))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Cart not found with id: 99");
    }

    @Test
    void GIVEN_non_existing_recipe_WHEN_add_recipes_THEN_not_found() {
        when(recipeService.findByIds(anySet())).thenReturn(Collections.emptySet());

        assertThatThrownBy(() -> cartService.addRecipes(1L, Set.of(99L)))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("One or more recipes not found: [99]");
    }

    @Test
    void GIVEN_cart_and_duplicated_recipe_WHEN_add_recipes_THEN_duplicate_recipe() {
        testCart.getRecipes().add(recipe1); // Add recipe1 to cart first

        when(recipeService.findByIds(anySet())).thenReturn(Set.of(recipe1));
        when(cartRepository.findWithRecipesAndProductsById(1L)).thenReturn(testCart);

        assertThatThrownBy(() -> cartService.addRecipes(1L, Set.of(1L)))
                .isInstanceOf(DuplicateRecipeException.class)
                .hasMessageContaining("Duplicate recipes found with IDs: [1]");
    }

    @Test
    void GIVEN__existing_and_non_existing_recipe_WHEN_add_recipes_THEN_not_found() {
        when(recipeService.findByIds(anySet())).thenReturn(Set.of(recipe1));

        assertThatThrownBy(() -> cartService.addRecipes(1L, Set.of(1L, 99L)))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("One or more recipes not found: [99]");
    }
}