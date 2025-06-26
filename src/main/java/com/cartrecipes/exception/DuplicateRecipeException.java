package com.cartrecipes.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
public class DuplicateRecipeException extends RuntimeException {
    private final Set<Long> duplicateRecipeIds;

    public DuplicateRecipeException(Set<Long> duplicateRecipeIds) {
        super("Duplicate recipes found with IDs: " + duplicateRecipeIds);
        this.duplicateRecipeIds = Set.copyOf(duplicateRecipeIds);
    }
}
