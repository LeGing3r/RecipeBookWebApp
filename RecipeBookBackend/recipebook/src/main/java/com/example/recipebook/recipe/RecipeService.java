package com.example.recipebook.recipe;

import com.example.recipebook.errors.RecipeNotFoundException;
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
import java.util.UUID;
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

    //tested
    Page<RecipePage> getRecipePage(int page, int size, boolean chosenRecipes) {
        var totalRecipes = recipeRepository.getTotalRecipes();
        var pageAmount = Math.max(totalRecipes / size, 1);
        var pageRequest = PageRequest.of(page, size);
        var newList = chosenRecipes ? recipeRepository.findChosenRecipes(pageRequest) : recipeRepository.findAll(pageRequest).getContent();
        var recipePageDTOS = convertRecipesToPageDTO(newList);

        return new Page(recipePageDTOS, pageAmount, page, size);
    }

    //tested
    RecipeDTO findRecipeById(UUID recipeId) throws SQLException {
        return getByPublicId(recipeId)
                .toRecipeDTO();
    }

    //tested
    Page<RecipePage> findRecipesByQuery(String name, QueryType queryType) {
        var recipes = switch (queryType) {
            case RECIPE -> recipeRepository.findByNameContainsIgnoreCase(name);
            case CATEGORY -> recipeRepository.findByCategoriesContainsIgnoreCase(name);
            case INGREDIENT -> recipeRepository.findByIngredientsContainsIgnoreCase(name);
        };
        var recipePageElements = convertRecipesToPageDTO(recipes);

        return new Page(recipePageElements, 1, 1, recipes.size());
    }

    //tested
    RecipeDTO addRecipe(RecipeDTO recipeDTO) {
        recipeDTO.id = UUID.randomUUID();

        var recipe = new Recipe(recipeDTO);

        recipeRepository.save(recipe);

        return new RecipeDTO(recipe);
    }

    //tested
    void updateRecipe(RecipeDTO recipeDTO) {
        var recipe = getByPublicId(recipeDTO.id);

        recipe.mergeWithRecipeDTO(recipeDTO);

        recipeRepository.save(recipe);
    }

    //tested
    void delete(UUID recipeId) throws SQLException {
        var recipe = getByPublicId(recipeId);

        recipeRepository.delete(recipe);
    }

    //tested
    void toggleChosen(UUID recipeId) throws SQLException {
        var recipe = getByPublicId(recipeId);

        recipe.chosen = !recipe.chosen;

        recipeRepository.save(recipe);
    }

    Page<String> getCategoryPage(int page, int size) {
        var startPoint = (page - 1) * size;
        var totalCategories = recipeRepository.getTotalCategories();
        var pagesAmount = Math.max(totalCategories / size, 1);
        var categories = recipeRepository
                .findAll()
                .stream()
                .flatMap(recipe -> recipe.categories.stream())
                .sorted(String::compareTo)
                .skip(startPoint)
                .limit(size)
                .collect(Collectors.toSet());

        return new Page<>(categories, pagesAmount, page, size);
    }

    //tested
    void saveImage(UUID id, MultipartFile image) throws IOException, URISyntaxException {
        var recipe = getByPublicId(id);
        var imageName = imageService.saveImage(image.getBytes(), recipe.id);

        recipe.imageLocation = imageName;
        recipeRepository.save(recipe);
    }

    //tested
    byte[] findImage(UUID id) throws IOException {
        var fileLocation = getByPublicId(id)
                .imageLocation;

        return imageService.loadImage(fileLocation);
    }

    //API CALL CANNOT BE TESTED
    NutritionalInfo getNutritionalInfo(UUID id) throws IOException, InterruptedException {
        var address = nutritionURL
                .formatted(nutritionId, nutritionKey);
        var url = getByPublicId(id)
                .getIngredients()
                .stream()
                .collect(Collectors.joining(" and ", address, ""));

        return getNutritionalInfo(url);
    }

    private Recipe getByPublicId(UUID id) {
        return recipeRepository.findByPublicId(id)
                .orElseThrow(RecipeNotFoundException::new);
    }

    private Set<RecipePage> convertRecipesToPageDTO(Collection<Recipe> recipes) {
        return recipes.stream()
                .map(RecipePage::new)
                .collect(Collectors.toSet());
    }

    private void deleteRecipeImage(String imageLocation) {
        Paths.get(imageLocation).toFile().delete();
    }

    private NutritionalInfo getNutritionalInfo(String url) throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        return new Gson().fromJson(
                client.send(request, HttpResponse.BodyHandlers.ofString()).body(),
                NutritionalInfo.class);
    }
}
