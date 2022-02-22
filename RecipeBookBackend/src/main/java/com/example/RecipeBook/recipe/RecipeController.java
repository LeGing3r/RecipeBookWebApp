package com.example.RecipeBook.recipe;

import com.example.RecipeBook.utils.Page;
import com.example.RecipeBook.utils.QueryType;
import com.example.RecipeBook.errors.RecipeNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe")
    public HttpEntity<RecipeDTO> getRecipe(@RequestParam UUID id) {
        try {
            var recipe = recipeService.findRecipeById(id);
            return new ResponseEntity<>(recipe, OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @PostMapping("/recipe")
    public HttpEntity<RecipeDTO> addRecipe(@RequestBody RecipeDTO recipe) {
        if (recipeService.addRecipe(recipe)) {
            return new ResponseEntity<>(recipe, OK);
        }
        return new ResponseEntity<>(BAD_REQUEST);
    }

    @PutMapping("/recipe")
    public HttpEntity<RecipeDTO> updateRecipe(@RequestBody RecipeDTO recipe) {
        if (recipeService.updateRecipe(recipe)) {
            return new ResponseEntity<>(recipe, OK);
        }
        return new ResponseEntity<>(NOT_ACCEPTABLE);
    }

    @DeleteMapping("/recipe")
    public HttpEntity<RecipeDTO> deleteRecipe(@RequestParam UUID id) {
        try {
            recipeService.delete(id);
            return new ResponseEntity<>(OK);

        } catch (SQLException e) {
            return new ResponseEntity<>(NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/recipe/choose")
    public HttpEntity<RecipeDTO> chooseRecipe(@RequestParam UUID id) {
        try {
            recipeService.chooseRecipe(id);
            return new ResponseEntity<>(OK);
        } catch (Exception e) {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/recipe/new")
    public HttpEntity<RecipeDTO> getNewRecipe() {
        return new ResponseEntity<>(new RecipeDTO(), OK);
    }

    @GetMapping("/recipes")
    public HttpEntity<Page<RecipePage>> getRecipePage(@RequestParam int page,
                                                      @RequestParam int size) {
        Page<RecipePage> recipePage = recipeService.getRecipePage(page, size, false);
        if (recipePage.getElements() == null || recipePage.getElements().isEmpty()) {
            return new ResponseEntity<>(NO_CONTENT);
        }
        return new ResponseEntity<>(recipePage, OK);
    }

    @GetMapping("/recipes/chosen")
    public HttpEntity<Page<RecipePage>> listChosenRecipes(@RequestParam int page,
                                                          @RequestParam int size) {
        Page<RecipePage> recipePage = recipeService.getRecipePage(page, size, true);
        if (recipePage.getElements() == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(recipePage, OK);
    }

    @GetMapping(value = "/recipe/image",
            produces = MediaType.IMAGE_PNG_VALUE)
    public HttpEntity<byte[]> getRecipeImage(@RequestParam UUID id) {
        try {
            var imageBytes = recipeService.findImage(id);
            return new ResponseEntity<>(imageBytes, OK);
        } catch (RecipeNotFoundException | IOException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }

    }

    @PostMapping(value = "/recipe/image", headers = {"content-type=multipart/form-data"})
    public HttpEntity<byte[]> setRecipeImage(@RequestParam UUID id, MultipartFile image) {
        try {
            recipeService.saveImage(id, image);
            return new ResponseEntity<>(OK);
        } catch (Exception e) {
            return new ResponseEntity<>(METHOD_NOT_ALLOWED);
        }
    }


    @GetMapping("/recipes/search")
    public HttpEntity<Page<RecipePage>> filterRecipes(@RequestParam String query, @RequestParam QueryType searchType) {
        Page<RecipePage> page = recipeService.findRecipesByQuery(query, searchType);
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
            return new ResponseEntity<>(NOT_FOUND);
        }

    }
}





