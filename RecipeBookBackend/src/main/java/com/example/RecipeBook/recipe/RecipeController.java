package com.example.RecipeBook.recipe;

import com.example.RecipeBook.recipe.model.RecipeDTO;
import com.example.RecipeBook.recipe.model.RecipePage;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Optional;
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
    public HttpEntity<RecipeDTO> getRecipe(@RequestParam UUID recipeId) {
        try {
            var recipe = recipeService.findRecipeById(recipeId);
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
    public HttpEntity<RecipeDTO> updateRecipe(@RequestParam UUID recipeId, @RequestBody RecipeDTO recipe) {
        if (recipeService.updateRecipe(recipeId, recipe)) {
            return new ResponseEntity<>(recipe, OK);
        }
        return new ResponseEntity<>(NOT_ACCEPTABLE);
    }

    @DeleteMapping("/recipe")
    public HttpEntity<RecipeDTO> deleteRecipe(@RequestParam UUID recipeId) {
        try {
            recipeService.delete(recipeId);
            return new ResponseEntity<>(OK);

        } catch (SQLException e) {
            return new ResponseEntity<>(NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/recipe/choose")
    public HttpEntity<RecipeDTO> chooseRecipe(@RequestParam UUID recipeId) {
        try {
            recipeService.chooseRecipe(recipeId);
            return new ResponseEntity<>(OK);
        } catch (Exception e) {
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/recipes")
    public HttpEntity<RecipePage> getRecipePage(@RequestParam Optional<Integer> page,
                                                @RequestParam Optional<Integer> size) {
        RecipePage recipePage = recipeService.findPages(size.orElse(8), page.orElse(1), false);
        if (recipePage.getRecipes() == null || recipePage.getRecipes().isEmpty()) {
            return new ResponseEntity<>(NO_CONTENT);
        }
        return new ResponseEntity<>(recipePage, OK);
    }

    @GetMapping("/recipes/chosen")
    public HttpEntity<RecipePage> listChosenRecipes(@RequestParam Optional<Integer> page,
                                                    @RequestParam Optional<Integer> size) {
        RecipePage recipePage = recipeService.findPages(size.orElse(8), page.orElse(1), true);
        if (recipePage.getRecipes() == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(recipePage, OK);
    }


    @GetMapping(
            value = "/recipe/image",
            produces = MediaType.IMAGE_PNG_VALUE)
    public HttpEntity<byte[]> getRecipeImage(@RequestParam String id) throws IOException {
        InputStream in = new FileInputStream("src/main/resources/images/1.png");
        return new ResponseEntity<>(in.readAllBytes(), OK);
    }

    @GetMapping("/search")
    public HttpEntity<RecipePage> filterRecipes(@RequestParam String query, @RequestParam String searchType) {
        RecipePage page = switch (searchType.toLowerCase()) {
            case "recipe" -> recipeService.findRecipesByName(query);
            case "category" -> recipeService.findRecipesByCategoryName(query);
            case "ingredient" -> recipeService.findRecipesByIngredientName(query);
            default -> null;
        };
        if (page == null)
            return new ResponseEntity<>(NOT_FOUND);
        return new ResponseEntity<>(page, OK);
    }

    @GetMapping("/recipes/total")
    public HttpEntity<Integer> getTotalRecipes() {
        return new ResponseEntity<>(recipeService.getTotalRecipes(), OK);
    }
}





