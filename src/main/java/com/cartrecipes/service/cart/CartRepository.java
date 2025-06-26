package com.cartrecipes.service.cart;

import com.cartrecipes.model.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface CartRepository extends JpaRepository<Cart, Long> {
    @EntityGraph(attributePaths = {"recipes", "products"})
    Optional<Cart> findWithRecipesAndProductsById(Long id);
}
