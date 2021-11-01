package com.example.RecipeBook.ingredient.impl;

import com.example.RecipeBook.ingredient.IngredientRepository;
import com.example.RecipeBook.ingredient.model.Ingredient;

public class DefaultIngredientRepository implements IngredientRepository {
    @Override
    public boolean save(Ingredient ingredient) {
        return false;
    }
}
