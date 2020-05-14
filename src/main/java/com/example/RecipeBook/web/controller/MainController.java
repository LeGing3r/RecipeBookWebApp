package com.example.RecipeBook.web.controller;

import com.example.RecipeBook.dao.CategoryRepository;
import com.example.RecipeBook.dao.IngredientRepository;
import com.example.RecipeBook.dao.RecipeRepository;
import com.example.RecipeBook.model.Category;
import com.example.RecipeBook.model.Ingredient;
import com.example.RecipeBook.model.Recipe;
import com.example.RecipeBook.service.RecipeService;
import com.example.RecipeBook.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RecipeService recipeService;

    @RequestMapping("")
    public String toMainSite() {
        return "redirect:/recipes";
    }

    @RequestMapping("/recipes/add")
    public String toFormNewRecipe() {
        return "redirect:/add";
    }

    @RequestMapping("/recipes/{recipeId}")
    public String showRecipeDetails(@PathVariable Integer recipeId, Model model) {
        Recipe recipe = recipeRepository.findById(recipeId).isPresent() ? recipeRepository.findById(recipeId).get() : null;
        model.addAttribute("recipe", recipe);
        model.addAttribute("categories", recipe.getCategories());
        return "recipe/details";
    }

    @RequestMapping("/recipes")
    public String listAllRecipes(Model model) {
        List<Recipe> recipes = recipeRepository.findAll();
        model.addAttribute("action", "/chosen");
        model.addAttribute("switch", "Recipes To Make");
        model.addAttribute("recipes", recipes);
        return "recipe/index";
    }

    @RequestMapping("/chosen")
    public String listChosenRecipes(Model model) {
        List<Recipe> recipes = recipeRepository.findAll();
        recipes = recipes
                .stream()
                .filter(Recipe::isChosen)
                .collect(Collectors.toList());
        model.addAttribute("action", "/recipes");
        model.addAttribute("switch", "All Recipes");
        model.addAttribute("recipes", recipes);

        return "recipe/index";
    }

    @RequestMapping("/add")
    public String formNewRecipe(Model model) {
        if (!model.containsAttribute("recipe")) {
            Recipe recipe = new Recipe();
            recipe.getIngredients().add(new Ingredient());
            model.addAttribute("recipe", recipe);
        }
        model.addAttribute("action", "/recipes");
        model.addAttribute("submit", "Add Recipe");
        return "recipe/form";
    }

    @RequestMapping("/recipes/{recipeId}/edit")
    public String formEditRecipe(@PathVariable Integer recipeId, Model model) {
        Recipe recipe = recipeRepository.findById(recipeId).isPresent() ? recipeRepository.findById(recipeId).get() : new Recipe();
        model.addAttribute("recipe", recipe);
        model.addAttribute("submit", "Edit Rcipe");
        model.addAttribute("action", "/recipes/" + recipeId);
        return "recipe/form";
    }

    @PostMapping("/recipes/{recipeId}")
    public String updateRecipe(@Valid Recipe recipe, RedirectAttributes attributes, BindingResult result) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("recipe", recipe);
            attributes.addFlashAttribute("submit", "Edit Rcipe");
            attributes.addFlashAttribute("action", "/recipes/" + recipe.getId());
            return String.format("redirect:/recipes/{%s}/edit", recipe.getId());
        }

        recipe.getIngredients().forEach(r -> r.setRecipe(recipe));
        recipeService.setCategories(recipe);
        ingredientRepository.saveAll(recipe.getIngredients());
        return "redirect:/recipes/" + recipe.getId();
    }

    @PostMapping(value = "recipes/{recipeId}/delete")
    public String deleteRecipe(@PathVariable Integer recipeId) {
        recipeService.delete(recipeId);
        return "redirect:/";
    }

    @PostMapping("/recipes")
    public String addRecipe(@Valid Recipe recipe, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipe", result);
            redirectAttributes.addFlashAttribute("recipe", recipe);
            return "redirect:/add";
        }

        recipe.getIngredients().forEach(ingredient -> ingredient.setRecipe(recipe));
        recipeService.setCategories(recipe);
        ingredientRepository.saveAll(recipe.getIngredients());

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Recipe successfully added!", FlashMessage.Status.SUCCESS));
        return "redirect:/recipes";
    }

    @PostMapping(value = {"/recipes", "/recipes/{recipeId}"}, params = {"addIngredient"})
    public String addIngredient(Recipe recipe, BindingResult bindingResult) {
        recipe.getIngredients().add(new Ingredient());
        return "recipe/form";
    }

    @PostMapping(value = {"/recipes", "/recipes/{recipeId}"}, params = {"removeIngredient"})
    public String deleteIngredient(Recipe recipe, HttpServletRequest request, BindingResult bindingResult) {
        int ingId = Integer.parseInt(request.getParameter("removeIngredient"));
        recipe.getIngredients().remove(ingId);
        return "recipe/form";
    }

    @PostMapping(value = {"/recipes", "/recipes/{recipeId}"}, params = {"addCategory"})
    public String addCat(Recipe recipe, BindingResult bindingResult, Model model) {
        recipe.getCategories().add(new Category());
        return "recipe/form";
    }

    @PostMapping(value = {"/recipes", "/recipes/{recipeId}"}, params = {"removeCat"})
    public String deleteCat(Recipe recipe, HttpServletRequest request, BindingResult bindingResult, Model model) {
        int ingId = Integer.parseInt(request.getParameter("removeCat"));
        recipe.getCategories().remove(ingId);
        return "recipe/form";
    }

    @RequestMapping("/ingredients/{ingredientId}")
    public String toggleIngredientNecessity(@PathVariable Integer ingredientId, HttpServletRequest http) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).isPresent() ? ingredientRepository.findById(ingredientId).get() : null;
        ingredient.setNeeded(!ingredient.isNeeded());
        ingredientRepository.save(ingredient);
        return "redirect:" + http.getHeader("referer");
    }

}
