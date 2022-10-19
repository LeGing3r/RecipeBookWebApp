package com.example.recipebook.recipe;

import com.example.recipebook.errors.RecipeNotFoundException;
import com.example.utils.Page;
import com.example.utils.QueryType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.example.utils.QueryType.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RecipeControllerIntegrationTest {

    @Autowired
    private RecipeController controller;

    @Value("${images.source}")
    private String imagesSource;

    @Test
    public void getRecipePage_getFirstRecipe_update_delete() {
        var recipePage = getRecipePage(1, 10);
        var recipes = recipePage.getElements().iterator();
        var recipeElement = recipes.next();
        var recipeDto = getRecipe(recipeElement.getId());

        recipeDto.categories.add("NewCategory");
        updateRecipe(recipeDto);

        removeRecipe(recipeDto);

        var newRecipeFirstPage = getRecipePage(1, 10);

        assertNotEquals(recipePage, newRecipeFirstPage);

        assertThrows(RecipeNotFoundException.class, () -> controller.updateRecipe(recipeDto));
    }

    @Test
    public void getChosenRecipePage_createRecipe_chooseRecipe() {
        var chosenPage = getRecipePageChosen(1, 10);

        assertTrue(chosenPage.getElements().isEmpty());

        var recipeDTO = createRecipeDao("FirstAddedRecipe");

        recipeDTO = addRecipe(recipeDTO);

        var chosenResponse = (ResponseEntity<RecipeDTO>) controller.chooseRecipe(recipeDTO.id);

        responseOK(chosenResponse);

        recipeDTO = controller.getRecipe(recipeDTO.id).getBody();

        assertNotNull(recipeDTO);

        assertTrue(recipeDTO.chosen);

        chosenPage = getRecipePageChosen(1, 10);

        assertFalse(chosenPage.getElements().isEmpty());

        removeRecipe(recipeDTO);
    }

    @Test
    public void addRemoveCategoriesAndIngredients() {
        var recipe = createRecipeDao("addRemoveCategoriesAndIngredients");
        var cat = "NewlyAddedCat";
        var ing = "newlyAddedIngredient";

        recipe = addRecipe(recipe);

        recipe.categories.add(cat);
        recipe.ingredients.add(ing);

        updateRecipe(recipe);

        recipe = getRecipe(recipe.id);

        assertTrue(recipe.categories.contains(cat));
        assertTrue(recipe.ingredients.contains(ing));

        recipe.categories.remove(cat);
        recipe.ingredients.remove(ing);

        updateRecipe(recipe);

        recipe = getRecipe(recipe.id);

        assertFalse(recipe.categories.contains(cat));
        assertFalse(recipe.ingredients.contains(ing));
    }

    @Test
    public void addCheckImage() throws IOException, URISyntaxException {
        try {
            var image = getTestImage();
            var page = getRecipePage(1, 2);
            var recipe = page.getElements().iterator().next();

            controller.setRecipeImage(recipe.getId(), image);

            var actualImage = getRecipeImage(recipe.getId());

            assertArrayEquals(image.getBytes(), actualImage);
        } finally {
            deleteImage();
        }
    }

    @Test
    public void queryTest() {
        var queryResult = filterQueries("soup", RECIPE);
        var emptyResult = filterQueries("alsdkfj", RECIPE);
        var recipeName = "queryTest";
        var cat = "catTest";
        var ing = "ingTest";

        assertTrue(queryResult.size() > 0);
        assertTrue(emptyResult.isEmpty());

        var recipe = createRecipeDao(recipeName);

        addRecipe(recipe);

        queryResult = filterQueries(recipeName, RECIPE);

        assertEquals(1, queryResult.size());

        recipe = getRecipe(recipe.id);

        recipe.ingredients.add(ing);
        recipe.categories.add(cat);

        updateRecipe(recipe);

        queryResult = filterQueries(cat, CATEGORY);

        assertEquals(1, queryResult.size());

        queryResult = filterQueries(ing, INGREDIENT);

        assertEquals(1, queryResult.size());

        var smallCategorySet = getCategories(1, 2);
        var largeCategorySet = getCategories(1, 10);
        var smallCategorySetPageTwo = getCategories(2, 2);

        assertTrue(largeCategorySet.containsAll(smallCategorySet));
        assertTrue(largeCategorySet.containsAll(smallCategorySetPageTwo));
        assertFalse(smallCategorySet.containsAll(smallCategorySetPageTwo));

    }

    private void updateRecipe(RecipeDTO recipeDTO) {
        assertNotNull(recipeDTO);

        var response = (ResponseEntity<RecipeDTO>) controller.updateRecipe(recipeDTO);

        responseOK(response);

        var recipe = response.getBody();

        assertEquals(recipeDTO, recipe);
    }

    private void removeRecipe(RecipeDTO recipe) {
        var response = (ResponseEntity<RecipeDTO>) controller.deleteRecipe(recipe.id);

        responseOK(response);

        assertNull(controller.getRecipe(recipe.id).getBody());

    }

    private void responseOK(ResponseEntity<?> response) {
        assertEquals(OK, response.getStatusCode());
    }

    private Page<RecipePageElement> getRecipePage(int pageNumber, int size) {
        var response = (ResponseEntity<Page<RecipePageElement>>) controller.getRecipePage(pageNumber, size);

        responseOK(response);

        var page = response.getBody();

        assertNotNull(page);

        assertTrue(page.getElements().size() > 0 && page.getElements().size() <= size);

        return page;
    }

    private Page<RecipePageElement> getRecipePageChosen(int pageNumber, int size) {
        var response = (ResponseEntity<Page<RecipePageElement>>) controller.getRecipePageChosen(pageNumber, size);

        responseOK(response);

        var page = response.getBody();

        assertNotNull(page);

        var recipes = page.getElements();

        assertNotNull(recipes);
        assertTrue(recipes.size() <= size);

        return page;
    }

    private RecipeDTO addRecipe(RecipeDTO recipeDTO) {
        var response = (ResponseEntity<RecipeDTO>) controller.addRecipe(recipeDTO);

        responseOK(response);

        var recipe = response.getBody();

        assertNotNull(recipe);
        assertNotNull(recipe.id);

        return recipe;
    }

    private RecipeDTO createRecipeDao(String name) {
        var recipe = new RecipeDTO();

        recipe.name = name;
        recipe.categories = new HashSet<>(Collections.singleton("Something"));
        recipe.ingredients = new HashSet<>(Collections.singleton("Oil"));
        recipe.instructions = "Cooka da fish";
        recipe.nutritionalInfo = new NutritionalInfo("googe.com", 100, 2.2,
                List.of("healhty stuff"), List.of("Smou"), List.of("die die bad"));
        recipe.cookingTime = new CookingTime(50, 50);
        recipe.portionSize = 44;

        return recipe;
    }

    private RecipeDTO getRecipe(UUID id) {
        var response = (ResponseEntity<RecipeDTO>) controller.getRecipe(id);

        responseOK(response);

        var recipe = response.getBody();

        assertNotNull(recipe);

        assertEquals(id, recipe.id);

        return recipe;
    }

    private Set<RecipePageElement> filterQueries(String query, QueryType queryType) {
        var response = (ResponseEntity<Page<RecipePageElement>>) controller.filterRecipes(query, queryType);

        responseOK(response);

        var page = response.getBody();

        assertNotNull(page);

        var recipes = page.getElements();

        assertNotNull(recipes);

        return recipes;
    }

    private Set<String> getCategories(int page, int size) {
        var response = (ResponseEntity<Page<String>>) controller.getCategoryPage(page, size);

        responseOK(response);

        var catPage = response.getBody();

        assertNotNull(catPage);
        assertEquals(page, catPage.getCurrentPage());

        var cats = catPage.getElements();

        assertNotNull(cats);
        assertTrue(cats.size() <= size);

        return cats;
    }

    private byte[] getRecipeImage(UUID id) {
        var response = (ResponseEntity<byte[]>) controller.getRecipeImage(id);

        responseOK(response);

        var image = response.getBody();

        assertNotNull(image);
        assertTrue(image.length > 0);

        return image;
    }

    private MultipartFile getTestImage() throws IOException {
        var image = getClass().getResourceAsStream("/images/example.png");

        assertNotNull(image);

        return new MockMultipartFile("dataFile", image.readAllBytes());
    }

    private void deleteImage() throws IOException {
        var directoryPath = Path.of(Paths.get("").toAbsolutePath() + imagesSource);
        Files.walk(directoryPath)
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().matches(".*\\d+\\.png"))
                .forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });


    }
}
