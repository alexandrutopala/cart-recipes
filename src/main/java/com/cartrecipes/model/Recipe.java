package com.cartrecipes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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
    @Min(0)
    private Integer priceInCents;

    @ManyToMany
    @JoinTable(
            name = "recipe_items",
            joinColumns = @JoinColumn(name = "recipe_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"recipe_id", "product_id"}, name = "uk_recipe_items")
    )
    private Set<Product> products = new HashSet<>();
}
