package com.example.RecipeBook.recipe;

import com.example.RecipeBook.category.model.Category;
import com.example.RecipeBook.ingredient.model.Ingredient;
import com.example.RecipeBook.recipe.model.Recipe;
import com.example.RecipeBook.recipe.model.RecipePage;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.UUID;

public interface RecipeService {
    RecipePage findPages(int pageSize, int currentPage, boolean chosenRecipes);

    Recipe findRecipeById(UUID recipeId) throws SQLException;

    RecipePage findRecipesByName(String name);

    RecipePage findRecipesByCategoryName(String categoryName);

    RecipePage findRecipesByIngredientName(String ingredientName);

    boolean addRecipe(Recipe recipe, MultipartFile multipartFile);

    boolean updateRecipe(Recipe recipe, MultipartFile multipartFile);

    boolean delete(UUID recipeId)  throws SQLException ;

    void toggleChosen(UUID recipeId)  throws SQLException ;

    boolean addIngredient(Recipe recipe, Ingredient ingredient);

    boolean removeIngredient(Recipe recipe, Ingredient ingredient);

    boolean removeCategory(Recipe recipe, Category category);

    boolean addCategory(Recipe recipe, Category category);
}
