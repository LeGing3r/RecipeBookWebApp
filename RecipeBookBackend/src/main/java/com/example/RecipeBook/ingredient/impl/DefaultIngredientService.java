package com.example.RecipeBook.ingredient.impl;

import com.example.RecipeBook.ingredient.IngredientRepository;
import com.example.RecipeBook.ingredient.IngredientService;
import com.example.RecipeBook.recipe.model.Recipe;

public class DefaultIngredientService implements IngredientService {
    private final IngredientRepository ingredientRepository;

    public DefaultIngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }


    @Override
    public void setIngredients(Recipe recipe) {
        recipe.getIngredients()
                .stream()
                .peek(ingredient -> ingredient.setRecipe(recipe))
                .forEach(ingredientRepository::save);
    }

}
