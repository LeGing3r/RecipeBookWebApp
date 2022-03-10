package com.example.RecipeBook.recipe;

import com.example.RecipeBook.utils.Page;
import com.example.RecipeBook.utils.QueryType;
import com.example.RecipeBook.errors.RecipeNotFoundException;
import com.example.RecipeBook.item.ItemService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.RecipeBook.utils.QueryType.CATEGORY;
import static com.example.RecipeBook.utils.QueryType.RECIPE;
import static org.apache.commons.io.FileUtils.copyInputStreamToFile;

@Service
public class RecipeService {
    @Value("${nutrition.app.key}")
    private String nutritionKey;
    @Value("${nutrition.app.id}")
    private String nutritionId;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private ItemService ingredientService;

    Page<RecipePage> getRecipePage(int currentPage, int size, boolean chosenRecipes) {
        int startPoint = (currentPage - 1) * size;
        int totalRecipes = recipeRepository.getTotal(RECIPE);
        int pageAmount = Math.max(totalRecipes / size, 1);
        var newList = recipeRepository
                .findRecipePage(startPoint, size, chosenRecipes)
                .orElseThrow(RecipeNotFoundException::new);
        Set<RecipePage> recipePageDTOS = convertRecipesToPageDTO(newList);
        return new Page(recipePageDTOS, pageAmount, size, currentPage);
    }

    RecipeDTO findRecipeById(UUID recipeId) throws SQLException {
        return recipeRepository
                .findRecipeById(recipeId)
                .orElseThrow(RecipeNotFoundException::new)
                .toRecipeDTO();
    }

    Page<RecipePage> findRecipesByQuery(String name, QueryType queryType) {
        Set<Recipe> recipes = recipeRepository
                .findRecipesByQuery(name, queryType)
                .orElseThrow(RecipeNotFoundException::new);
        var recipePageElements = convertRecipesToPageDTO(recipes);
        return new Page(recipePageElements, 1, 1, 8);
    }

    boolean addRecipe(RecipeDTO recipe) {
        var recipeCopy = new Recipe(recipe);
        return recipeRepository.addRecipe(recipeCopy);
    }

    boolean updateRecipe(RecipeDTO recipe) {
        try {
            return recipeRepository.updateRecipe(recipe);
        } catch (Exception e) {
            return false;
        }
    }

    boolean delete(UUID recipeId) throws SQLException {
        return recipeRepository.delete(recipeId);
    }

    void toggleChosen(UUID recipeId) throws SQLException {
        Recipe r = recipeRepository.findRecipeById(recipeId).orElseThrow(RecipeNotFoundException::new);
        r.chosen = !r.chosen;
        recipeRepository.addRecipe(r);
    }

    boolean addIngredient(RecipeDTO recipe, String ingredient) {
        if (recipe.addIngredients(List.of(ingredient))) {
            return recipeRepository.updateRecipe(recipe);
        }
        return false;
    }

    boolean removeIngredient(RecipeDTO recipe, String ingredient) {
        if (recipe.removeIngredients(List.of(ingredient))) {
            return recipeRepository.updateRecipe(recipe);
        }
        return false;
    }

    boolean addCategory(RecipeDTO recipeDTO, String category) {
        if (recipeDTO.addCategories(List.of(category))) {
            return recipeRepository.updateRecipe(recipeDTO);
        }
        return false;
    }

    boolean removeCategory(RecipeDTO recipeDTO, String category) {
        if (recipeDTO.removeCategories(List.of(category))) {
            var recipeDao = new Recipe(recipeDTO);
            return recipeRepository.addRecipe(recipeDao);
        }
        return false;
    }

    void saveImage(UUID id, MultipartFile image) throws IOException {
        var recipe = recipeRepository.findRecipeById(id).orElseThrow(RecipeNotFoundException::new);
        String pathname = "D:/Projects/RecipeBookWebApp/RecipeBookBackend/src/main/resources/images/" + recipe.id + ".png";
        recipeRepository.updateRecipe(recipe, pathname);
        copyInputStreamToFile(image.getInputStream(), new File(pathname));
    }

    byte[] findImage(UUID id) throws IOException {
        var fileLocation = recipeRepository.getImageLocation(id);
        return new FileInputStream(fileLocation).readAllBytes();
    }

    Optional<NutritionalInfo> getNutritionalInfo(UUID id) {
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
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    void chooseRecipe(UUID recipeId) throws SQLException {
        recipeRepository.switchChosenOfRecipe(recipeId);
    }

    Page<String> getCategoryPage(int page, int size) {
        int startPoint = (page - 1) * size;
        int totalCategories = recipeRepository.getTotal(CATEGORY);
        int pagesAmount = Math.max(totalCategories / size, 1);
        var categories = recipeRepository.findCategories(startPoint, size);
        return new Page<>(categories, pagesAmount, page, size);
    }

    private Set<RecipePage> convertRecipesToPageDTO(Set<Recipe> recipes) {
        return recipes.stream()
                .map(this::convertRecipeToPageElement)
                .collect(Collectors.toSet());
    }

    private RecipePage convertRecipeToPageElement(Recipe recipe) {
        return new RecipePage(recipe);
    }

    private void deleteRecipeImage(String imageLocation) {
        Paths.get(imageLocation).toFile().delete();
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
