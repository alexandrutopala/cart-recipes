package com.cartrecipes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "carts")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cart {
    @Id
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "total_in_cents", nullable = false)
    @Min(0)
    private Integer totalInCents;

    @ManyToMany
    @JoinTable(
            name = "cart_items",
            joinColumns = @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"cart_id", "product_id"}, name = "uk_cart_items")
    )
    private Collection<Product> products = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "cart_recipes",
            joinColumns = @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "id", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"cart_id", "recipe_id"}, name = "uk_cart_recipes")
    )
    private Collection<Recipe> recipes = new HashSet<>();
}
