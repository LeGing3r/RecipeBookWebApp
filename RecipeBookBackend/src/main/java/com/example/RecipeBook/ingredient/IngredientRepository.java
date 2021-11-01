package com.example.RecipeBook.ingredient;

import com.example.RecipeBook.ingredient.model.Ingredient;

public interface IngredientRepository {
    boolean save(Ingredient ingredient);
}
