package com.example.RecipeBook.web.controller;

import com.example.RecipeBook.dao.IngredientRepository;
import com.example.RecipeBook.dao.IngredientsCreationDto;
import com.example.RecipeBook.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class IngredientsController {
    @Autowired
    IngredientRepository ingredientRepository;

    @RequestMapping("/ingredients")
    public String findAllIngredients(){
        ingredientRepository.findAll().forEach(System.out::println);
        return "redirect:/todo";
    }

    @RequestMapping("/todo")
    public String displayShoppingList(Model model) {
        List<Ingredient> ingredients = ingredientRepository.findAll()
                .stream()
                .filter(Ingredient::isNeeded)
                .collect(Collectors.toList());
        IngredientsCreationDto form = new IngredientsCreationDto();
        form.getIngredients().addAll(ingredients);
        model.addAttribute("cart", form);
        model.addAttribute("newIngredient", new Ingredient());
        return "shopping/index";
    }

    @RequestMapping(value = "/todo", method = RequestMethod.POST)
    public String updateTodoList(IngredientsCreationDto form, BindingResult result) {
            ingredientRepository.saveAll(form.getIngredients());
        return "redirect:/todo";
    }

    @PostMapping("/todo/add")
    public String addToList(@Valid Ingredient ingredient, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.binding.ingredient", result);
            return "redirect:/todo";
        }
        ingredientRepository.save(ingredient);
        return "redirect:/todo";
    }
}
