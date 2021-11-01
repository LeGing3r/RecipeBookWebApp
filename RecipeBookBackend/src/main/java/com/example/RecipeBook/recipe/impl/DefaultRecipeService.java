package com.example.RecipeBook.recipe.impl;

import com.example.RecipeBook.category.CategoryService;
import com.example.RecipeBook.category.model.Category;
import com.example.RecipeBook.errors.RecipeNotFoundException;
import com.example.RecipeBook.ingredient.IngredientService;
import com.example.RecipeBook.ingredient.model.Ingredient;
import com.example.RecipeBook.recipe.RecipeRepository;
import com.example.RecipeBook.recipe.RecipeService;
import com.example.RecipeBook.recipe.model.Recipe;
import com.example.RecipeBook.recipe.model.RecipePage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.example.RecipeBook.StaticStrings.*;

@Service
public class DefaultRecipeService implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final CategoryService categoryService;
    private final IngredientService ingredientService;


    public DefaultRecipeService(RecipeRepository recipeRepository,
                                CategoryService categoryService,
                                IngredientService ingredientService) {
        this.recipeRepository = recipeRepository;
        this.categoryService = categoryService;
        this.ingredientService = ingredientService;
    }

    public RecipePage findPages(int pageSize, int currentPage, boolean chosenRecipes) {

        Set<Recipe> newList;
        if (chosenRecipes) {
            newList = recipeRepository.findRecipePage(currentPage, pageSize).orElseThrow(RecipeNotFoundException::new);
        } else {
            newList = recipeRepository.findChosen(currentPage, pageSize).orElseThrow(RecipeNotFoundException::new);
        }
        return new RecipePage(newList, pageSize, currentPage);
    }

    public Recipe findRecipeById(UUID recipeId) throws SQLException {
        return recipeRepository.findRecipeById(recipeId).orElseThrow(RecipeNotFoundException::new);
    }

    public RecipePage findRecipesByName(String name) {
        Set<Recipe> recipes = recipeRepository
                .findRecipesByName(name)
                .orElseThrow(RecipeNotFoundException::new);
        return new RecipePage(recipes, 1, 1);
    }

    public RecipePage findRecipesByCategoryName(String categoryName) {
        Set<Recipe> recipes = recipeRepository
                .findRecipesByCategory(categoryName)
                .orElseThrow(RecipeNotFoundException::new);
        return new RecipePage(recipes, 1, 1);
    }

    public RecipePage findRecipesByIngredientName(String ingredientName) {
        Set<Recipe> recipes = recipeRepository
                .findRecipesByIngredient(ingredientName)
                .orElseThrow(RecipeNotFoundException::new);
        return new RecipePage(recipes, 1, 1);
    }

    public boolean addRecipe(Recipe recipe, MultipartFile file) {
        try {
            ingredientService.setIngredients(recipe);
            categoryService.setCategories(recipe);
            setImgLoc(recipe, file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return recipeRepository.saveAndFlush(recipe);
    }

    public boolean updateRecipe(Recipe recipe,
                                MultipartFile file) {
        try {
            ingredientService.setIngredients(recipe);
            categoryService.setCategories(recipe);
            setImgLoc(recipe, file);
        } catch (Exception e) {
            return false;
        }
        return recipeRepository.saveAndFlush(recipe);
    }

    public boolean delete(UUID recipeId) throws SQLException {
        Recipe recipe = recipeRepository
                .findRecipeById(recipeId)
                .orElseThrow(RecipeNotFoundException::new);
        categoryService.findCategoryByRecipeName(recipe.getName())
                .forEach(cat -> categoryService.deleteRecipeFromCategory(recipe, cat));
        deleteRecipeImage(recipe.getImageLocation());
        return recipeRepository.delete(recipe);
    }

    public void toggleChosen(UUID recipeId) throws SQLException {
        Recipe r = recipeRepository.findRecipeById(recipeId).orElseThrow(RecipeNotFoundException::new);
        r.switchChosen();
        recipeRepository.saveAndFlush(r);
    }

    public boolean addIngredient(Recipe recipe, Ingredient ingredient) {
        recipe.addIngredients(List.of(ingredient));
        return recipeRepository.saveAndFlush(recipe);
    }

    public boolean removeIngredient(Recipe recipe, Ingredient ingredient) {
        recipe.removeIngredients(List.of(ingredient));
        return recipeRepository.saveAndFlush(recipe);
    }

    public boolean removeCategory(Recipe recipe, Category category) {
        recipe.removeCategories(List.of(category));
        return recipeRepository.saveAndFlush(recipe);
    }

    public boolean addCategory(Recipe recipe, Category category) {
        recipe.addCategories(List.of(category));
        return recipeRepository.saveAndFlush(recipe);
    }

    private void setImgLoc(Recipe recipe, MultipartFile file) throws IOException {

        String path = IMG_LOC + recipe.getId() + PNG;
        File ogFile = Paths.get(path).toFile();
        if (ogFile.exists()) {
            ogFile.delete();
        }
        file.transferTo(new File(path));

        Path tempPath = Paths.get(IMG_TEMP_LOC + recipe.getId() + PNG);
        Files.copy(Paths.get(path), tempPath, StandardCopyOption.REPLACE_EXISTING);

        recipe.setImageLocation("/images/" + recipe.getId() + PNG);
        recipe.setImageLocation(path);

    }

    private void setCategories(Recipe recipe) {
        recipe.getCategories()
                .forEach(category -> category.addRecipe(recipe));
        recipe.getCategories()
                .forEach(categoryService::saveCategory);
    }

    private void deleteRecipeImage(String imageLocation) {
        Paths.get(imageLocation).toFile().delete();
    }

}
