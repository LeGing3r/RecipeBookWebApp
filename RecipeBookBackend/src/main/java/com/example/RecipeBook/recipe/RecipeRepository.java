package com.example.RecipeBook.recipe;

import com.example.RecipeBook.recipe.model.Recipe;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface RecipeRepository {
    Optional<Recipe> findRecipeById(UUID recipeId) throws SQLException;

    Optional<Set<Recipe>> findRecipePage(int startPoint, int numberOfRecipes);

    boolean saveAndFlush(Recipe recipe);

    boolean delete(Recipe recipe);

    Optional<Set<Recipe>> findRecipesByIngredient(String name);

    Optional<Set<Recipe>> findChosen(int startPoint, int numberOfRecipes);

    Optional<Set<Recipe>> findRecipesByName(String query);

    Optional<Set<Recipe>> findRecipesByCategory(String categoryName);
}
