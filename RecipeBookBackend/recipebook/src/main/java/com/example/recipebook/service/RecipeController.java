package com.example.recipebook.service;

import com.example.recipebook.errors.RecipeNotFoundException;
import com.example.recipebook.recipe.Recipe;
import com.example.utils.Page;
import com.example.utils.QueryType;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe")
    public HttpEntity<Recipe> getRecipe(@RequestParam String id) {
        try {
            return new ResponseEntity<>(recipeService.findRecipeById(id), OK);
        } catch (SQLException | RecipeNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @PostMapping("/recipe")
    public HttpEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
        return new ResponseEntity<>(recipeService.addRecipe(recipe), OK);
    }

    @PutMapping("/recipe")
    public HttpEntity<Recipe> updateRecipe(@RequestBody Recipe recipe) {
        recipeService.updateRecipe(recipe);
        return new ResponseEntity<>(recipe, OK);
    }

    @DeleteMapping("/recipe")
    public HttpEntity<Recipe> deleteRecipe(@RequestParam String id) {
        try {
            recipeService.delete(id);
            return new ResponseEntity<>(OK);

        } catch (SQLException e) {
            return new ResponseEntity<>(NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/recipe/choose")
    public HttpEntity<Recipe> chooseRecipe(@RequestParam String id) {
        try {
            recipeService.toggleChosen(id);
            return new ResponseEntity<>(OK);
        } catch (Exception e) {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/recipe/new")
    public HttpEntity<Recipe> getNewRecipe() {
        return new ResponseEntity<>(new Recipe(), OK);
    }

    @GetMapping("/recipes")
    public HttpEntity<Page<Recipe>> getRecipePage(@RequestParam int page,
                                                             @RequestParam int size) {
        var recipePage = recipeService.getRecipePage(page, size, false);

        if (recipePage.getElements() == null || recipePage.getElements().isEmpty()) {
            return new ResponseEntity<>(NO_CONTENT);
        }
        return new ResponseEntity<>(recipePage, OK);
    }

    @GetMapping("/recipes/chosen")
    public HttpEntity<Page<Recipe>> getRecipePageChosen(@RequestParam int page,
                                                                   @RequestParam int size) {
        var recipePage = recipeService.getRecipePage(page, size, true);

        return new ResponseEntity<>(recipePage, OK);
    }

    @GetMapping(value = "/recipe/image",
            produces = MediaType.IMAGE_PNG_VALUE)
    public HttpEntity<byte[]> getRecipeImage(@RequestParam String id) {
        try {
            return new ResponseEntity<>(recipeService.findImage(id), OK);
        } catch (RecipeNotFoundException | IOException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }

    }

    @PostMapping(value = "/recipe/image", headers = {"content-type=multipart/form-data"})
    public HttpEntity<byte[]> setRecipeImage(@RequestParam String id,
                                             @RequestParam("image") MultipartFile image) throws IOException, URISyntaxException {
        recipeService.saveImage(id, image);
        return new ResponseEntity<>(OK);
    }

    @GetMapping("/recipes/search")
    public HttpEntity<Page<Recipe>> filterRecipes(@RequestParam String query, @RequestParam QueryType searchType) {
        Page<Recipe> page = recipeService.findRecipesByQuery(query, searchType);
        if (page == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(page, OK);
    }

    @GetMapping("/categories")
    public HttpEntity<Page<String>> getCategoryPage(@RequestParam int page, @RequestParam int size) {
        try {
            return new ResponseEntity<>(recipeService.getCategoryPage(page, size), OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(NOT_FOUND);
        }

    }
}
