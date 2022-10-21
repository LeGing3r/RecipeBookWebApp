package com.example.recipebook.service;

import com.example.recipebook.errors.RecipeNotFoundException;
import com.example.recipebook.recipe.NutritionalInfo;
import com.example.recipebook.recipe.Recipe;
import com.example.recipebook.recipe.RecipePageElement;
import com.example.utils.Page;
import com.example.utils.QueryType;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class RecipeService {
    @Value("${nutrition.app.key:null}")
    private String nutritionKey;
    @Value("${nutrition.app.id:null}")
    private String nutritionId;
    @Value("${nutritionURL:null}")
    private String nutritionURL;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private ImageService imageService;

    Page<RecipePageElement> getRecipePage(int page, int size, boolean chosenRecipes) {
        var totalRecipes = (int) recipeRepository.count();
        var pageAmount = Math.max(totalRecipes / size, 1);
        var pageRequest = PageRequest.of(page - 1, size);
        var newList = chosenRecipes ? recipeRepository.findByChosenTrue(pageRequest) :
                recipeRepository.findAll(pageRequest).getContent();
        var recipePageDTOS = convertRecipesToPageDTO(newList);

        return new Page(recipePageDTOS, pageAmount, page, size);
    }

    Page<RecipePageElement> findRecipesByQuery(String name, QueryType queryType) {
        var recipes = switch (queryType) {
            case RECIPE -> recipeRepository.findByNameContainsIgnoreCase(name);
            case CATEGORY -> recipeRepository.findByCategories(name);
            case INGREDIENT -> recipeRepository.findByIngredients(name);
        };
        var recipePageElements = convertRecipesToPageDTO(recipes);

        return new Page(recipePageElements, 1, 1, recipes.size());
    }

    Page<String> getCategoryPage(int page, int size) {
        var startPoint = (page - 1) * size;
        var totalCategories = recipeRepository.findAll()
                .stream()
                .flatMap(r -> r.getCategories().stream())
                .distinct()
                .collect(Collectors.toList())
                .size();
        var pagesAmount = Math.max(totalCategories / size, 1);
        var categories = recipeRepository
                .findAll()
                .stream()
                .flatMap(recipe -> recipe.getCategories().stream())
                .sorted(String::compareTo)
                .skip(startPoint)
                .limit(size)
                .collect(Collectors.toSet());

        return new Page<>(categories, pagesAmount, page, size);
    }

    Recipe findRecipeById(String recipeId) throws SQLException {
        return getById(recipeId);
    }

    Recipe addRecipe(Recipe recipe) {
        return recipeRepository.insert(recipe);
    }

    NutritionalInfo getNutritionalInfo(String id) throws IOException, InterruptedException {
        var address = nutritionURL
                .formatted(nutritionId, nutritionKey);
        var url = getById(id)
                .getIngredients()
                .stream()
                .collect(Collectors.joining(" and ", address, ""));

        return getNutritionalInfo(URI.create(url));
    }

    void delete(String recipeId) throws SQLException {
        var recipe = getById(recipeId);

        recipeRepository.delete(recipe);
    }

    void toggleChosen(String recipeId) throws SQLException {
        var recipe = getById(recipeId);

        recipe.switchChosen();

        recipeRepository.save(recipe);
    }

    void saveImage(String id, MultipartFile image) throws IOException, URISyntaxException {
        var recipe = getById(id);
        var imageName = imageService.saveImage(image.getBytes(), recipe.getId());

        recipe.setImageLocation(imageName);
        recipeRepository.save(recipe);
    }

    byte[] findImage(String id) throws IOException {
        var fileLocation = getById(id)
                .getImageLocation();

        return imageService.loadImage(fileLocation);
    }

    private Recipe getById(String id) {
        return recipeRepository.findById(id)
                .orElseThrow(RecipeNotFoundException::new);
    }

    private Set<RecipePageElement> convertRecipesToPageDTO(Collection<Recipe> recipes) {
        return recipes.stream()
                .map(RecipePageElement::new)
                .collect(Collectors.toSet());
    }

    private void deleteRecipeImage(String imageLocation) {
        Paths.get(imageLocation).toFile().delete();
    }

    private NutritionalInfo getNutritionalInfo(URI url) throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder()
                .uri(url)
                .build();

        return new Gson().fromJson(
                client.send(request, HttpResponse.BodyHandlers.ofString()).body(),
                NutritionalInfo.class);
    }

    public void updateRecipe(Recipe recipe) {
        if (recipeRepository.existsById(recipe.getId())) {
            recipeRepository.save(recipe);
        } else {
            throw new RecipeNotFoundException();
        }
    }
}
