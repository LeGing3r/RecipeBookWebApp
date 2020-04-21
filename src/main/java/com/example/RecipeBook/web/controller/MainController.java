package com.example.RecipeBook.web.controller;

import com.example.RecipeBook.dao.RecipeRepository;
import com.example.RecipeBook.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/demo")
public class MainController {
    @Autowired
    private RecipeRepository recipeRepository;

    @PostMapping(path = "/add")
    public @ResponseBody
    String addNewRecipe(@RequestParam String name, @RequestParam String ingredient) {
        Recipe r = new Recipe();
        r.setName(name);
        r.setIngredient(ingredient);
        recipeRepository.save(r);
        return "Saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }
}
