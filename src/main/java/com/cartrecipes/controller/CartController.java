package com.cartrecipes.controller;

import com.cartrecipes.controller.dto.cart.CartDto;
import com.cartrecipes.controller.dto.recipe.AddRecipesToCartDto;
import com.cartrecipes.service.cart.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CartController.BASE_PATH)
@RequiredArgsConstructor
@Validated
public class CartController {
    public static final String BASE_PATH = "/carts";

    private final CartService cartService;

    private final ModelMapper modelMapper;

    @GetMapping("/{cartId}")
    public CartDto findById(@PathVariable Long cartId) {
        var cart = cartService.findWithRecipesAndProductsById(cartId);
        return modelMapper.map(cart, CartDto.class);
    }

    @PostMapping("/{cartId}/add_recipe")
    public CartDto addRecipes(@PathVariable Long cartId, @RequestBody @Valid AddRecipesToCartDto addRecipesToCartDto) {
        var cart = cartService.addRecipes(cartId, addRecipesToCartDto.recipeIds());
        return modelMapper.map(cart, CartDto.class);
    }

    @DeleteMapping("/{cartId}/recipes/{recipeId}")
    public CartDto removeRecipe(@PathVariable Long cartId, @PathVariable Long recipeId) {
        var cart = cartService.removeRecipe(cartId, recipeId);
        return modelMapper.map(cart, CartDto.class);
    }
}
