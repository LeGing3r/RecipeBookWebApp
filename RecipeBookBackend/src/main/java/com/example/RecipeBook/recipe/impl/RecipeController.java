package com.example.RecipeBook.recipe.impl;

import com.example.RecipeBook.category.model.Category;
import com.example.RecipeBook.item.model.Item;
import com.example.RecipeBook.recipe.RecipeService;
import com.example.RecipeBook.recipe.model.Recipe;
import com.example.RecipeBook.recipe.model.RecipePage;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController("/api/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(DefaultRecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/")
    public HttpEntity<Recipe> addRecipe(Recipe recipe, @RequestParam MultipartFile file) {
        if (recipeService.addRecipe(recipe, file)) {
            return new ResponseEntity<>(OK);
        }
        return new ResponseEntity<>(BAD_REQUEST);
    }

    @RequestMapping("/{recipeId}")
    public HttpEntity<Recipe> showRecipeDetails(@PathVariable UUID recipeId) {
        try {
            Recipe recipe = recipeService.findRecipeById(recipeId);
            return new ResponseEntity<>(recipe, OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @PostMapping("/{recipeId}")
    public HttpEntity<Recipe> updateRecipe(Recipe recipe, MultipartFile file) {
        if (recipeService.updateRecipe(recipe, file)) {
            return new ResponseEntity<>(recipe, OK);
        }
        return new ResponseEntity<>(NOT_ACCEPTABLE);
    }

    @DeleteMapping("/{recipeId}")
    public HttpEntity<Recipe> deleteRecipe(@PathVariable UUID recipeId) {
        try {
            recipeService.delete(recipeId);
            return new ResponseEntity<>(OK);

        } catch (SQLException e) {
            return new ResponseEntity<>(NOT_ACCEPTABLE);
        }
    }

    @RequestMapping("/{page}/{size}")
    public HttpEntity<RecipePage> listAllRecipes(@RequestParam("page") Integer page,
                                                 @RequestParam("size") Integer size) {
        RecipePage recipePage = recipeService.findPages(page, size, false);
        if (recipePage.getRecipes() == null || recipePage.getRecipes().isEmpty()) {
            return new ResponseEntity<>(OK);
        }
        return new ResponseEntity<>(recipePage, OK);
    }

    @RequestMapping("/chosen")
    public HttpEntity<RecipePage> listChosenRecipes(@RequestParam("page") int page,
                                                    @RequestParam("size") int size) {
        RecipePage recipePage = recipeService.findPages(page, size, true);
        if (recipePage.getRecipes() == null || recipePage.getRecipes().isEmpty()) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(recipePage, OK);
    }


    @PostMapping("/addIngredient")
    public HttpEntity<Recipe> addIngredient(Recipe recipe, Item item) {
        if (recipeService.addIngredient(recipe, item)) {
            return new ResponseEntity<>(OK);
        }
        return new ResponseEntity<>(NOT_ACCEPTABLE);
    }

    @PostMapping("/removeIngredient")
    public HttpEntity<Recipe> deleteIngredient(Recipe recipe, Item item) {
        if (recipeService.removeIngredient(recipe, item)) {
            return new ResponseEntity<>(OK);
        }
        return new ResponseEntity<>(NOT_FOUND);
    }

    @PostMapping("/addCategory")
    public HttpEntity<Recipe> addCategory(Recipe recipe, Category category) {
        if (recipeService.addCategory(recipe, category)) {
            return new ResponseEntity<>(OK);
        }
        return new ResponseEntity<>(NOT_ACCEPTABLE);
    }

    @PostMapping("/removeCategory")
    public HttpEntity<Recipe> deleteCategory(Recipe recipe, Category category) {
        if (recipeService.removeCategory(recipe, category)) {
            return new ResponseEntity<>(OK);
        }
        return new ResponseEntity<>(NOT_FOUND);
    }

    @RequestMapping("/search")
    public HttpEntity<RecipePage> filterRecipes(@RequestParam String query, @RequestParam String searchType) {
        return switch (searchType.toLowerCase()) {
            case "recipe" -> new ResponseEntity<>(recipeService.findRecipesByName(query), OK);
            case "category" -> new ResponseEntity<>(recipeService.findRecipesByCategoryName(query), OK);
            case "ingredient" -> new ResponseEntity<>(recipeService.findRecipesByIngredientName(query), OK);
            default -> new ResponseEntity<>(NOT_FOUND);
        };
    }

    /*
    TODO: HANDLE IN FRONT END WHY DO YOU NEED TO TALK TO THE API
    @RequestMapping("/{id}/nutrition")
    public HttpEntity<NutritionalInfo> getRecipeNutrition(@RequestParam UUID id) {
        try {
            return new ResponseEntity<>(recipeService.getNutritionalInfo(id), OK);
        } catch (Exception e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }*/

       /*
    TODO MOVE TO FRONTEND
    @RequestMapping("/copy")
    public HttpEntity<Recipe> copyRecipeFromSite(Recipe recipe) {

        return "recipe/copy-form";
    }

    @PostMapping("/copy")
    public HttpEntity<Recipe> sendCopiedForm(ConversionObj contents, RedirectAttributes attributes) {
        Recipe recipe = conversionObjService.getRecipeFromConversion(contents);
        if (contents.getName() != null)
            recipe.setName(contents.getName());
        attributes.addFlashAttribute("recipe", recipe);
        return "redirect:/add";
    }*/

      /*
    TODO MOVE THIS TO FRONTEND
    @RequestMapping("/add")
    public String newRecipeForm(Recipe recipe) {
        if (!model.containsAttribute("recipe")) {
            Recipe recipe = new Recipe();
            recipe.getIngredients().add(new Ingredient());
            model.addAttribute("recipe", recipe);
        }
        model.addAttribute("action", "/recipes");
        model.addAttribute("submit", "Add Recipe");
        return "recipe/form";
    }*/

   /*
    TODO MOVE TO FRONTEND
    @RequestMapping(value = "/add", params = "withCategory")
    public String formNewRecipeWithCat(Model model, Category cat) {
        Recipe recipe = new Recipe();
        recipe.getCategories().add(categoryService.findById(cat.getId()));
        recipe.getIngredients().add(new Ingredient());

        model.addAttribute("recipe", recipe);
        model.addAttribute("action", "/recipes");
        model.addAttribute("submit", "Add Recipe");
        return "recipe/form";
    }*/

    /*
    TODO MOVE TO FRONTEND
    @RequestMapping("/{recipeId}/edit")
    public String formEditRecipe(@PathVariable Integer recipeId, Model model) {
        Recipe recipe = recipeService.findRecipeById(recipeId);
        model.addAttribute("recipe", recipe);
        model.addAttribute("submit", "Edit Rcipe");
        model.addAttribute("action", "/recipes/" + recipeId);
        try {
            model.addAttribute("img", Files.readAllBytes(Paths.get(recipe.getImageLocation())));
        } catch (IOException e) {
            System.out.println(Paths.get(recipe.getImageLocation()));
        }
        return "recipe/form";
    }
    */
}





