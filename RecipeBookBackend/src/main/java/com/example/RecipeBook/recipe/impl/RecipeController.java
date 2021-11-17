package com.example.RecipeBook.recipe.impl;

import com.example.RecipeBook.recipe.RecipeService;
import com.example.RecipeBook.recipe.model.Recipe;
import com.example.RecipeBook.recipe.model.RecipePage;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(value = "/api/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(DefaultRecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe")
    public HttpEntity<Recipe> getRecipe(@RequestParam UUID recipeId) {
        try {
            Recipe recipe = recipeService.findRecipeById(recipeId);
            return new ResponseEntity<>(recipe, OK);
        } catch (SQLException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @PostMapping("/recipe")
    public HttpEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
        if (recipeService.addRecipe(recipe)) {
            return new ResponseEntity<>(recipe, OK);
        }
        return new ResponseEntity<>(BAD_REQUEST);
    }

    @PutMapping("/recipe")
    public HttpEntity<Recipe> updateRecipe(@RequestParam UUID recipeId, @RequestBody Recipe recipe) {
        if (recipeService.updateRecipe(recipeId, recipe)) {
            return new ResponseEntity<>(recipe, OK);
        }
        return new ResponseEntity<>(NOT_ACCEPTABLE);
    }

    @DeleteMapping("/recipe")
    public HttpEntity<Recipe> deleteRecipe(@RequestParam UUID recipeId) {
        try {
            recipeService.delete(recipeId);
            return new ResponseEntity<>(OK);

        } catch (SQLException e) {
            return new ResponseEntity<>(NOT_ACCEPTABLE);
        }
    }

    @GetMapping
    public HttpEntity<RecipePage> getRecipePage(@RequestParam Optional<Integer> page,
                                                @RequestParam Optional<Integer> size) {
        RecipePage recipePage = recipeService.findPages(size.orElse(8), page.orElse(1), false);
        if (recipePage.getRecipes() == null || recipePage.getRecipes().isEmpty()) {
            return new ResponseEntity<>(NO_CONTENT);
        }
        return new ResponseEntity<>(recipePage, OK);
    }

    @GetMapping("/chosen")
    public HttpEntity<RecipePage> listChosenRecipes(@RequestParam Optional<Integer> page,
                                                    @RequestParam Optional<Integer> size) {
        RecipePage recipePage = recipeService.findPages(size.orElse(8), page.orElse(1), true);
        if (recipePage.getRecipes() == null || recipePage.getRecipes().isEmpty()) {
            return new ResponseEntity<>(NOT_FOUND);
        }
        return new ResponseEntity<>(recipePage, OK);
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

//    @PostMapping("/addIngredient")
//    public HttpEntity<Recipe> addIngredient(Recipe recipe, Item item) {
//        if (recipeService.addIngredient(recipe, item)) {
//            return new ResponseEntity<>(OK);
//        }
//        return new ResponseEntity<>(NOT_ACCEPTABLE);
//    }

//    @PostMapping("/removeIngredient")
//    public HttpEntity<Recipe> deleteIngredient(Recipe recipe, Item item) {
//        if (recipeService.removeIngredient(recipe, item)) {
//            return new ResponseEntity<>(OK);
//        }
//        return new ResponseEntity<>(NOT_FOUND);
//    }

//    @PostMapping("/addCategory")
//    public HttpEntity<Recipe> addCategory(Recipe recipe, Category category) {
//        if (recipeService.addCategory(recipe, category)) {
//            return new ResponseEntity<>(OK);
//        }
//        return new ResponseEntity<>(NOT_ACCEPTABLE);
//    }
//
//    @PostMapping("/removeCategory")
//    public HttpEntity<Recipe> deleteCategory(Recipe recipe, Category category) {
//        if (recipeService.removeCategory(recipe, category)) {
//            return new ResponseEntity<>(OK);
//        }
//        return new ResponseEntity<>(NOT_FOUND);
//    }
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





