package com.example.RecipeBook.web.controller;

import com.example.RecipeBook.dao.CategoryRepository;
import com.example.RecipeBook.dao.IngredientRepository;
import com.example.RecipeBook.dao.RecipeRepository;
import com.example.RecipeBook.model.Category;
import com.example.RecipeBook.model.ConversionObj;
import com.example.RecipeBook.model.Ingredient;
import com.example.RecipeBook.model.Recipe;
import com.example.RecipeBook.service.ConversionObjService;
import com.example.RecipeBook.service.RecipeService;
import com.example.RecipeBook.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class RecipeController {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private ConversionObjService conversionObjService;


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
        Recipe recipe = recipeRepository.findRecipeById(recipeId);
        model.addAttribute("recipe", recipe);
        model.addAttribute("categories", recipe.getCategories());
        return "recipe/details";
    }

    @RequestMapping("/recipes")
    public String listAllRecipes(Model model,
                                 @RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size") Optional<Integer> size) {

        Page<Recipe> recipePage = getRecipes(model, page.orElse(1), size.orElse(6), recipeRepository.findAll());


        model.addAttribute("link", "/recipes");
        model.addAttribute("action", "/chosen");
        model.addAttribute("switch", "Recipes To Make");
        model.addAttribute("recipePage", recipePage);
        return "recipe/index";
    }

    @RequestMapping("/chosen")
    public String listChosenRecipes(Model model,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size) {
        List<Recipe> recipes = recipeRepository.findAll()
                .stream()
                .filter(Recipe::isChosen)
                .collect(Collectors.toList());
        Page<Recipe> recipePage = getRecipes(model, page.orElse(1), size.orElse(6), recipes);

        model.addAttribute("link", "/chosen");
        model.addAttribute("action", "/recipes");
        model.addAttribute("switch", "All Recipes");
        model.addAttribute("recipePage", recipePage);
        model.addAttribute("title", "Recipes To Make");
        return "recipe/index";
    }

    private Page<Recipe> getRecipes(Model model, int page, int size, List<Recipe> recipes) {
        Page<Recipe> recipePage = recipeService.findPages(PageRequest.of(page - 1, size), recipes);

        int totalPages = recipePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("currentPage", page);
        }
        return recipePage;
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

    @RequestMapping(value = "/add", params = "withCategory")
    public String formNewRecipeWithCat(Model model, Category cat) {
        Recipe recipe = new Recipe();
        recipe.getCategories().add(categoryRepository.findById(cat.getId()).get());
        recipe.getIngredients().add(new Ingredient());

        model.addAttribute("recipe", recipe);
        model.addAttribute("action", "/recipes");
        model.addAttribute("submit", "Add Recipe");
        return "recipe/form";
    }

    @RequestMapping("/recipes/{recipeId}/edit")
    public String formEditRecipe(@PathVariable Integer recipeId, Model model) {
        Recipe recipe = recipeRepository.findRecipeById(recipeId);
        model.addAttribute("recipe", recipe);
        model.addAttribute("submit", "Edit Rcipe");
        model.addAttribute("action", "/recipes/" + recipeId);
        try {
            model.addAttribute("img", Files.readAllBytes(Paths.get(recipe.getImgPath())));
        } catch (IOException e) {
            System.out.println(Paths.get(recipe.getImgLoc()));
        }
        return "recipe/form";
    }

    @PostMapping("/recipes/{recipeId}")
    public String updateRecipe(@Valid Recipe recipe, RedirectAttributes attributes, BindingResult result, MultipartFile file) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("recipe", recipe);
            attributes.addFlashAttribute("submit", "Edit Rcipe");
            attributes.addFlashAttribute("flash", new FlashMessage("org.springframework.validation.BindingResult.recipe", FlashMessage.Status.FAILURE));
            attributes.addFlashAttribute("action", "/recipes/" + recipe.getId());
            return String.format("redirect:/recipes/{%s}/edit", recipe.getId());
        }

        recipe.getIngredients().forEach(r -> r.setRecipe(recipe));
        recipeService.setCategories(recipe);
        ingredientRepository.saveAll(recipe.getIngredients());
        recipeService.saveImgLoc(recipe, file);
        return "redirect:/recipes/" + recipe.getId();
    }

    @PostMapping(value = "recipes/{recipeId}/delete")
    public String deleteRecipe(@PathVariable Integer recipeId) {
        recipeService.delete(recipeId);
        return "redirect:/";
    }

    @PostMapping("/recipes")
    public String addRecipe(@Valid Recipe recipe, @RequestParam MultipartFile file, BindingResult result,
                            RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipe", result);
            redirectAttributes.addFlashAttribute("recipe", recipe);
            return "redirect:/add";
        }
        recipe.setImgLoc(System.getProperty("user.dir"));

        recipe.getIngredients().forEach(ingredient -> ingredient.setRecipe(recipe));
        recipeService.setCategories(recipe);
        ingredientRepository.saveAll(recipe.getIngredients());
        recipeService.saveImgLoc(recipe, file);

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

    @RequestMapping("/recipes/choose/{recipeId}")
    public String toggleRecipeChosen(@PathVariable Integer recipeId, HttpServletRequest http) {
        recipeService.toggleChosen(recipeId);
        return "redirect:" + http.getHeader("referer");
    }

    @RequestMapping("/recipes/copy")
    public String copyRecipeFromSite(Model model) {
        model.addAttribute("content", new ConversionObj());
        return "recipe/copy-form";
    }

    @PostMapping("/recipes/copy")
    public String sendCopiedForm(ConversionObj contents, RedirectAttributes attributes) {
        Recipe recipe = conversionObjService.getRecipeFromConversion(contents);
        if (contents.getName() != null)
            recipe.setName(contents.getName());
        attributes.addFlashAttribute("recipe", recipe);
        return "redirect:/add";
    }

    @GetMapping("/search")
    public String filterRecipes(@RequestParam String query, @RequestParam String searchType, Model model) {
        List<Recipe> recipes = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        String addedText = ":";
        switch (searchType.toLowerCase()) {
            case "recipe":
                recipes = recipeRepository.findAll()
                        .stream()
                        .filter(r -> r.getName().toLowerCase().contains(query.toLowerCase()))
                        .collect(Collectors.toList());
                break;
            case "category":
                for (Category cat : categoryRepository.findByCatName(query.toLowerCase().trim())) {
                    recipes.addAll(cat.getRecipes());
                    categories.add(cat);
                }
                addedText = " as a category: ";
                model.addAttribute("categories", categories);
                break;
            case "ingredient":
                recipes.addAll(recipeService.findRecipesByIngredientName(query));
                addedText = " as an ingredient: ";
                break;
            default:
                System.out.println("HOW????!!!!");
                System.out.println(searchType);
                return "redirect:/recipes";
        }
        Page<Recipe> recipePage = getRecipes(model, 1, recipes.size(), recipes);

        model.addAttribute("link", "/search");
        model.addAttribute("action", "/chosen");
        model.addAttribute("switch", "Recipes To Make");
        model.addAttribute("recipePage", recipePage);
        model.addAttribute("title", "Recipes with " + query + addedText);
        return "recipe/index";
    }

}





