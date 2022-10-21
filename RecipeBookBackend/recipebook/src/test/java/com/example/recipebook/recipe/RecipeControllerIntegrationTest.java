package com.example.recipebook.recipe;

import com.example.recipebook.errors.RecipeNotFoundException;
import com.example.recipebook.service.RecipeController;
import com.example.utils.Page;
import com.example.utils.QueryType;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.InsertManyOptions;
import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.example.utils.QueryType.CATEGORY;
import static com.example.utils.QueryType.INGREDIENT;
import static com.example.utils.QueryType.RECIPE;
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
        var recipe = getRecipe(recipeElement.getId());

        recipe.categories.add("NewCategory");
        updateRecipe(recipe);

        removeRecipe(recipe);

        var newRecipeFirstPage = getRecipePage(1, 10);

        assertNotEquals(recipePage, newRecipeFirstPage);

        assertThrows(RecipeNotFoundException.class, () -> controller.updateRecipe(recipe));
    }

    @Test
    public void getChosenRecipePage_createRecipe_chooseRecipe() {
        var chosenPage = getRecipePageChosen(1, 10);

        assertTrue(chosenPage.getElements().isEmpty());

        var recipe = createRecipeDao("FirstAddedRecipe");

        recipe = addRecipe(recipe);

        var chosenResponse = (ResponseEntity<Recipe>) controller.chooseRecipe(recipe.id);

        responseOK(chosenResponse);

        recipe = controller.getRecipe(recipe.id).getBody();

        assertNotNull(recipe);

        assertTrue(recipe.chosen);

        chosenPage = getRecipePageChosen(1
                , 10);

        assertFalse(chosenPage.getElements().isEmpty());

        removeRecipe(recipe);
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

        removeRecipe(recipe);
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

        controller.deleteRecipe(recipe.id);

    }

    @BeforeAll
    static void setUpDB() {
        var uri = "mongodb://test:test@localhost:27021/recipebook-test";
        try (var client = MongoClients.create(uri);
             var resources = Thread.currentThread().getContextClassLoader().getResourceAsStream("test-data.json")) {

            assert resources != null;

            var recipes = JsonParser.parseReader(new InputStreamReader(resources)).getAsJsonArray();
            var recipeArray = StreamSupport.stream(recipes.spliterator(), false)
                    .map(JsonElement::toString)
                    .map(Document::parse)
                    .collect(Collectors.toList());
            var collection = client.getDatabase("recipebook-test").getCollection("recipes");
            var existingRecipes = StreamSupport.stream(collection.find().spliterator(), false).collect(Collectors.toList());

            if (!existingRecipes.containsAll(recipeArray)) {
                collection.insertMany(recipeArray, new InsertManyOptions().ordered(false));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateRecipe(Recipe recipe) {
        assertNotNull(recipe);

        var response = (ResponseEntity<Recipe>) controller.updateRecipe(recipe);

        responseOK(response);

        var persistedRecipe = response.getBody();

        assertEquals(persistedRecipe, persistedRecipe);
    }

    private void removeRecipe(Recipe recipe) {
        var response = (ResponseEntity<Recipe>) controller.deleteRecipe(recipe.id);

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

    private Recipe addRecipe(Recipe recipe) {
        var response = (ResponseEntity<Recipe>) controller.addRecipe(recipe);

        responseOK(response);

        var persistedRecipe = response.getBody();

        assertNotNull(persistedRecipe);
        assertNotNull(persistedRecipe.id);

        return persistedRecipe;
    }

    private Recipe createRecipeDao(String name) {
        new Recipe();
        return new Recipe(null, name, "", false,
                new CookingTime(50, 50),
                new NutritionalInfo("googe.com", 100, 2.2, List.of("healhty stuff"), List.of("Smou"), List.of("die die bad")),
                44, "Cooka da fish", new HashSet<>(Set.of("Oil")), new HashSet<>(Set.of("Something")));
    }

    private Recipe getRecipe(String id) {
        var response = (ResponseEntity<Recipe>) controller.getRecipe(id);

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

    private byte[] getRecipeImage(String id) {
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
