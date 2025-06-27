package com.cartrecipes.exception

class DuplicateRecipeException(duplicateRecipeIds: Collection<Long>) : RuntimeException("Duplicate recipes found with IDs: $duplicateRecipeIds")