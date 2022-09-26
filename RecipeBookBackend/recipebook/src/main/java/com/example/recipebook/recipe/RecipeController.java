package com.example.recipebook.recipe;

import com.example.recipebook.errors.RecipeNotFoundException;
import com.example.utils.Page;
import com.example.utils.QueryType;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
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
            return new ResponseEntity<>(recipeService.findRecipeById(id), OK);
        } catch (SQLException | RecipeNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @PostMapping("/recipe")
    public HttpEntity<RecipeDTO> addRecipe(@RequestBody RecipeDTO recipeDTO) {
        var recipe = recipeService.addRecipe(recipeDTO);

        return new ResponseEntity<>(recipe, OK);
    }

    @PutMapping("/recipe")
    public HttpEntity<RecipeDTO> updateRecipe(@RequestBody RecipeDTO recipe) {
        recipeService.updateRecipe(recipe);
        return new ResponseEntity<>(recipe, OK);
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
            recipeService.toggleChosen(id);
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
        var recipePage = recipeService.getRecipePage(page, size, false);

        if (recipePage.getElements() == null || recipePage.getElements().isEmpty()) {
            return new ResponseEntity<>(NO_CONTENT);
        }
        return new ResponseEntity<>(recipePage, OK);
    }

    @GetMapping("/recipes/chosen")
    public HttpEntity<Page<RecipePage>> getRecipePageChosen(@RequestParam int page,
                                                            @RequestParam int size) {
        var recipePage = recipeService.getRecipePage(page, size, true);

        return new ResponseEntity<>(recipePage, OK);
    }

    @GetMapping(value = "/recipe/image",
            produces = MediaType.IMAGE_PNG_VALUE)
    public HttpEntity<byte[]> getRecipeImage(@RequestParam UUID id) {
        try {
            return new ResponseEntity<>(recipeService.findImage(id), OK);
        } catch (RecipeNotFoundException | IOException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }

    }

    @PostMapping(value = "/recipe/image", headers = {"content-type=multipart/form-data"})
    public HttpEntity<byte[]> setRecipeImage(@RequestParam UUID id,
                                             @RequestParam("image") MultipartFile image) throws IOException, URISyntaxException {
        recipeService.saveImage(id, image);
        return new ResponseEntity<>(OK);
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
            e.printStackTrace();
            return new ResponseEntity<>(NOT_FOUND);
        }

    }
}
