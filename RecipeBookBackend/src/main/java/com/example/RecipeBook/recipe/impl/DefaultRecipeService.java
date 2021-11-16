package com.example.RecipeBook.recipe.impl;

import com.example.RecipeBook.category.CategoryService;
import com.example.RecipeBook.category.model.Category;
import com.example.RecipeBook.errors.RecipeNotFoundException;
import com.example.RecipeBook.item.ItemService;
import com.example.RecipeBook.item.model.Item;
import com.example.RecipeBook.recipe.RecipeRepository;
import com.example.RecipeBook.recipe.RecipeService;
import com.example.RecipeBook.recipe.model.Recipe;
import com.example.RecipeBook.recipe.model.RecipePage;
import com.example.RecipeBook.recipe.model.nutiritional.NutritionalInfo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DefaultRecipeService implements RecipeService {
    @Value("${nutrition.app.key}")
    private String nutritionKey;
    @Value("${nutrition.app.id}")
    private String nutritionId;
    private final RecipeRepository recipeRepository;
    private final CategoryService categoryService;
    private final ItemService ingredientService;

    public DefaultRecipeService(RecipeRepository recipeRepository,
                                CategoryService categoryService,
                                ItemService ingredientService) {
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
        return new RecipePage(newList, pageSize, currentPage, pageSize);
    }

    public Recipe findRecipeById(UUID recipeId) throws SQLException {
        return recipeRepository.findRecipeById(recipeId).orElseThrow(RecipeNotFoundException::new);
    }

    public RecipePage findRecipesByName(String name) {
        Set<Recipe> recipes = recipeRepository
                .findRecipesByName(name)
                .orElseThrow(RecipeNotFoundException::new);
        return new RecipePage(recipes, 1, 1, 8);
    }

    public RecipePage findRecipesByCategoryName(String categoryName) {
        Set<Recipe> recipes = recipeRepository
                .findRecipesByCategory(categoryName)
                .orElseThrow(RecipeNotFoundException::new);
        return new RecipePage(recipes, 1, 1, 8);
    }

    public RecipePage findRecipesByIngredientName(String ingredientName) {
        Set<Recipe> recipes = recipeRepository
                .findRecipesByIngredient(ingredientName)
                .orElseThrow(RecipeNotFoundException::new);
        return new RecipePage(recipes, 1, 1, 8);
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

    public boolean addIngredient(Recipe recipe, Item item) {
        return recipe.addIngredients(List.of(item)) && recipeRepository.saveAndFlush(recipe);
    }

    public boolean removeIngredient(Recipe recipe, Item item) {
        if (recipe.removeIngredients(List.of(item)))
            return recipeRepository.saveAndFlush(recipe);
        return false;
    }

    public boolean addCategory(Recipe recipe, Category category) {
        return recipe.addCategories(List.of(category)) && recipeRepository.saveAndFlush(recipe);
    }

    public boolean removeCategory(Recipe recipe, Category category) {
        return recipe.removeCategories(List.of(category)) && recipeRepository.saveAndFlush(recipe);
    }

    private void setImgLoc(Recipe recipe, MultipartFile file) throws IOException {

        String path = "D:\\Projects\\RecipeBookWebApp\\src\\main\\resources\\images\\" + recipe.getId() + ".png";
        File ogFile = Paths.get(path).toFile();
        if (ogFile.exists()) {
            ogFile.delete();
        }
        file.transferTo(new File(path));

        Path tempPath = Paths.get("D:\\Projects\\RecipeBookWebApp\\target\\classes\\images\\" + recipe.getId() + ".png");
        Files.copy(Paths.get(path), tempPath, StandardCopyOption.REPLACE_EXISTING);

        recipe.setImageLocation("/images/" + recipe.getId() + ".png");
        recipe.setImageLocation(path);

    }

    private void deleteRecipeImage(String imageLocation) {
        Paths.get(imageLocation).toFile().delete();
    }

    public Optional<NutritionalInfo> getNutritionalInfo(UUID id) {
        try {
            String address = "https://api.edamam.com/api/nutrition-data?app_id=%s&app_key=%s"
                    .formatted(nutritionId, nutritionKey);
            String url = recipeRepository
                    .findRecipeById(id)
                    .orElseThrow(RecipeNotFoundException::new)
                    .getIngredients()
                    .stream()
                    .map(Item::toString)
                    .collect(Collectors.joining(" and ", address, ""));
            return Optional.of(getNutritionalInfo(url));
        } catch (SQLException | IOException | InterruptedException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private NutritionalInfo getNutritionalInfo(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        return new Gson().fromJson(
                client.send(request, HttpResponse.BodyHandlers.ofString()).body(),
                NutritionalInfo.class);
    }
}

/*
 * "totalNutrients": {
 * "VITD": {
 * "label": "Vitamin D (D2 + D3)",
 * "quantity": 0,
 * "unit": "µg"
 * },
 * },
 */
