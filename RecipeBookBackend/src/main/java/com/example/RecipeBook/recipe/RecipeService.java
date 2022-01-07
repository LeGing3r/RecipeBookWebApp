package com.example.RecipeBook.recipe;

import com.example.RecipeBook.category.CategoryService;
import com.example.RecipeBook.category.model.Category;
import com.example.RecipeBook.category.model.CategoryWithoutRecipes;
import com.example.RecipeBook.errors.RecipeNotFoundException;
import com.example.RecipeBook.item.ItemService;
import com.example.RecipeBook.item.model.Item;
import com.example.RecipeBook.nutiritional.NutritionalInfo;
import com.example.RecipeBook.recipe.model.Recipe;
import com.example.RecipeBook.recipe.model.RecipeDTO;
import com.example.RecipeBook.recipe.model.RecipePage;
import com.example.RecipeBook.recipe.model.RecipePageElement;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    @Value("${nutrition.app.key}")
    private String nutritionKey;
    @Value("${nutrition.app.id}")
    private String nutritionId;
    private final RecipeRepository recipeRepository;
    private final CategoryService categoryService;
    private final ItemService ingredientService;

    public RecipeService(RecipeRepository recipeRepository,
                         CategoryService categoryService,
                         ItemService ingredientService) {
        this.recipeRepository = recipeRepository;
        this.categoryService = categoryService;
        this.ingredientService = ingredientService;
    }

    public RecipePage findPages(int pageSize, int currentPage, boolean chosenRecipes) {

        Set<Recipe> newList = new HashSet<>();

        int startPoint = (currentPage - 1) * pageSize;
        if (chosenRecipes) {
            newList.addAll(recipeRepository
                    .findChosen(startPoint, pageSize)
                    .orElseThrow(RecipeNotFoundException::new));
        } else {
            newList.addAll(recipeRepository
                    .findRecipePage(startPoint, pageSize)
                    .orElseThrow(RecipeNotFoundException::new));
        }
        Set<RecipePageElement> recipePageElements = convertRecipesToPageElements(newList);
        int pageAmount = recipeRepository.getTotalRecipes();
        return new RecipePage(recipePageElements, pageAmount, currentPage, pageSize);
    }

    public RecipeDTO findRecipeById(UUID recipeId) throws SQLException {
        return recipeRepository
                .findRecipeById(recipeId)
                .orElseThrow(RecipeNotFoundException::new)
                .toRecipeDTO();
    }

    public RecipePage findRecipesByName(String name) {
        Set<Recipe> recipes = recipeRepository
                .findRecipesByName(name)
                .orElseThrow(RecipeNotFoundException::new);
        var recipePageElements = convertRecipesToPageElements(recipes);
        return new RecipePage(recipePageElements, 1, 1, 8);
    }

    public RecipePage findRecipesByCategoryName(String categoryName) {
        Set<Recipe> recipes = recipeRepository
                .findRecipesByCategory(categoryName)
                .orElseThrow(RecipeNotFoundException::new);
        var recipePageElements = convertRecipesToPageElements(recipes);
        return new RecipePage(recipePageElements, 1, 1, 8);
    }

    private Set<RecipePageElement> convertRecipesToPageElements(Set<Recipe> recipes) {
        return recipes.stream().map(RecipePageElement::new).collect(Collectors.toSet());
    }

    public RecipePage findRecipesByIngredientName(String ingredientName) {
        Set<Recipe> recipes = recipeRepository
                .findRecipesByIngredient(ingredientName)
                .orElseThrow(RecipeNotFoundException::new);
        var recipePageElements = convertRecipesToPageElements(recipes);
        return new RecipePage(recipePageElements, 1, 1, 8);
    }

    public boolean addRecipe(RecipeDTO recipe) {
        var recipeCopy = new Recipe(recipe);
        return recipeRepository.addRecipe(recipeCopy);
    }

    public boolean updateRecipe(UUID publicId, RecipeDTO recipe) {
        try {
            var recipeDao = new Recipe(recipe);
            return recipeRepository.updateRecipe(recipeDao);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(UUID recipeId) throws SQLException {
        return recipeRepository.delete(recipeId);
    }

    public void toggleChosen(UUID recipeId) throws SQLException {
        Recipe r = recipeRepository.findRecipeById(recipeId).orElseThrow(RecipeNotFoundException::new);
        r.switchChosen();
        recipeRepository.addRecipe(r);
    }

    public boolean addIngredient(RecipeDTO recipe, String ingredient) {
        recipe.addIngredients(List.of(ingredient));
        var recipeDao = new Recipe(recipe);
        return recipeRepository.updateRecipe(recipeDao);
    }

    public boolean removeIngredient(RecipeDTO recipe, String ingredient) {
        if (recipe.removeIngredients(List.of(ingredient))) {
            var recipeDao = new Recipe(recipe);
            return recipeRepository.updateRecipe(recipeDao);
        }
        return false;
    }

    public boolean addCategory(RecipeDTO recipeDTO, CategoryWithoutRecipes category) {
        if (recipeDTO.addCategories(List.of(category))) {
            var recipeDao = new Recipe(recipeDTO);
            return recipeRepository.updateRecipe(recipeDao);
        }
        return false;
    }

    public boolean removeCategory(RecipeDTO recipeDTO, CategoryWithoutRecipes category) {
        if (recipeDTO.removeCategories(List.of(category))) {
            var recipeDao = new Recipe(recipeDTO);
            return recipeRepository.addRecipe(recipeDao);
        }
        return false;
    }

    public Integer getTotalRecipes() {
        return recipeRepository.getTotalRecipes();
    }

    private void setImgLoc(RecipeDTO recipeDTO, MultipartFile file) throws IOException {

        String path = "D:\\Projects\\RecipeBookWebApp\\src\\main\\resources\\images\\" + recipeDTO.getId() + ".png";
        File ogFile = Paths.get(path).toFile();
        if (ogFile.exists()) {
            ogFile.delete();
        }
        file.transferTo(new File(path));

        Path tempPath = Paths.get("D:\\Projects\\RecipeBookWebApp\\target\\classes\\images\\" + recipeDTO.getId() + ".png");
        Files.copy(Paths.get(path), tempPath, StandardCopyOption.REPLACE_EXISTING);

        recipeDTO.setImageLocation("/images/" + recipeDTO.getId() + ".png");
        recipeDTO.setImageLocation(path);

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

    public void chooseRecipe(UUID recipeId) throws SQLException {
        Recipe recipe = recipeRepository
                .findRecipeById(recipeId)
                .orElseThrow(RecipeNotFoundException::new);
        recipe.switchChosen();
        recipeRepository.updateRecipe(recipe);
    }
}
