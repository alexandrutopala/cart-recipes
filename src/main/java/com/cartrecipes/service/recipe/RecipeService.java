package com.cartrecipes.service.recipe;

import com.cartrecipes.model.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Transactional(readOnly = true)
    public Page<Recipe> findAll(Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Collection<Recipe> findByIds(Collection<Long> ids) {
        return recipeRepository.findAllById(ids);
    }

    @Transactional(readOnly = true)
    public Recipe findById(Long id) {
        return recipeRepository.findWithProductsById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found with id: " + id));
    }
}
