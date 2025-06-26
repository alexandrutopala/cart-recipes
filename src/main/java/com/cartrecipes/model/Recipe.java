package com.cartrecipes.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Recipe {
    @Id
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price_in_cents", nullable = false)
    private Integer priceInCents;

    @ManyToMany
    @JoinTable(
            name = "recipe_items",
            joinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"recipe_id", "product_id"}, name = "uk_recipe_items")
    )
    private Collection<Product> products = new HashSet<>();
}
