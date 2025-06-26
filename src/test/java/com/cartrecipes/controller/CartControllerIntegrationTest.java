package com.cartrecipes.controller;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Set;

import com.cartrecipes.CartRecipesApplication;
import com.cartrecipes.config.H2Config;
import com.cartrecipes.controller.dto.recipe.AddRecipesToCartDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = {H2Config.class, CartRecipesApplication.class})
@AutoConfigureMockMvc
class CartControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void GIVEN_empty_cart_and_recipe_WHEN_add_recipes_THEN_recipe_added_and_total_updated() throws Exception {
        AddRecipesToCartDto request = new AddRecipesToCartDto(Set.of(1L));

        mockMvc.perform(post(("/carts/1000/add_recipe"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1000))
                .andExpect(jsonPath("$.totalInCents").value(1800));
    }

    @Test
    void GIVEN_empty_cart_and_multiple_recipe_WHEN_add_recipes_THEN_recipes_added_and_total_updated() throws Exception {
        AddRecipesToCartDto request = new AddRecipesToCartDto(Set.of(1L, 2L));

        mockMvc.perform(post("/carts/1001/add_recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1001))
                .andExpect(jsonPath("$.totalInCents").value(4000));
    }

    @Test
    void GIVEN_non_existing_cart_WHEN_add_recipes_THEN_not_found() throws Exception {
        AddRecipesToCartDto request = new AddRecipesToCartDto(Set.of(1L));

        mockMvc.perform(post("/carts/99/add_recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("NotFoundException"))
                .andExpect(jsonPath("$.details").value("Cart not found with id: 99"))
                .andExpect(jsonPath("$.statusCode").value(404));
    }

    @Test
    void GIVEN_non_existing_recipe_WHEN_add_recipes_THEN_not_found() throws Exception {
       AddRecipesToCartDto request = new AddRecipesToCartDto(Set.of(99L));

        mockMvc.perform(post("/carts/1/add_recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("NotFoundException"))
                .andExpect(jsonPath("$.details").value(containsString("One or more recipes not found")))
                .andExpect(jsonPath("$.statusCode").value(404));
    }

    @Test
    void GIVEN_duplicate_recipe_WHEN_add_recipes_THEN_not_found() throws Exception {
        AddRecipesToCartDto request = new AddRecipesToCartDto(Set.of(1L));

        mockMvc.perform(post("/carts/1002/add_recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("DuplicateRecipeException"))
                .andExpect(jsonPath("$.details").value(equalTo("Duplicate recipes found with IDs: [1]")))
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    @Test
    void GIVEN_missing_payload_WHEN_add_recipes_THEN_bad_request() throws Exception {
        AddRecipesToCartDto request = new AddRecipesToCartDto(Set.of());

        mockMvc.perform(post("/carts/1/add_recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void GIVEN_cart_with_recipe_WHEN_remove_recipe_THEN_recipe_removed_and_total_updated() throws Exception {
        mockMvc.perform(delete("/carts/1003/recipes/1003"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1003))
                .andExpect(jsonPath("$.totalInCents").value(2000));
    }

    @Test
    void GIVEN_non_existing_recipe_WHEN_remove_recipe_THEN_not_found() throws Exception {
        mockMvc.perform(delete("/carts/1/recipes/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("NotFoundException"))
                .andExpect(jsonPath("$.details").value(containsString("Recipe not found with id: 99")))
                .andExpect(jsonPath("$.statusCode").value(404));
    }

    @Test
    void GIVEN_non_existing_cart_WHEN_remove_recipe_THEN_not_found() throws Exception {
        mockMvc.perform(delete("/carts/99/recipes/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("NotFoundException"))
                .andExpect(jsonPath("$.details").value(containsString("Cart not found with id: 99")))
                .andExpect(jsonPath("$.statusCode").value(404));
    }

    @Test
    void GIVEN_cart_with_products_and_recipes_WHEN_find_by_id_THEN_cart_found() throws Exception {
        mockMvc.perform(get("/carts/1004"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1004))
                .andExpect(jsonPath("$.totalInCents").value(2500))
                .andExpect(jsonPath("$.products.length()").value(1))
                .andExpect(jsonPath("$.products[0].id").value(1004))
                .andExpect(jsonPath("$.products[0].name").value("Product 1"))
                .andExpect(jsonPath("$.products[0].priceInCents").value(1000))
                .andExpect(jsonPath("$.recipes.length()").value(1))
                .andExpect(jsonPath("$.recipes[0].id").value(1004))
                .andExpect(jsonPath("$.recipes[0].name").value("Recipe 1"))
                .andExpect(jsonPath("$.recipes[0].priceInCents").value(1500));
    }

    @Test
    void GIVEN_non_existing_cart_WHEN_find_by_id_THEN_not_found() throws Exception {
        mockMvc.perform(get("/carts/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("NotFoundException"))
                .andExpect(jsonPath("$.details").value(containsString("Cart not found with id: 99")))
                .andExpect(jsonPath("$.statusCode").value(404));
    }

}