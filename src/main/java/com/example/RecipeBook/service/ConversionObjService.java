package com.example.RecipeBook.service;

import com.example.RecipeBook.dao.IngredientRepository;
import com.example.RecipeBook.dao.ItemRepository;
import com.example.RecipeBook.model.ConversionObj;
import com.example.RecipeBook.model.Ingredient;
import com.example.RecipeBook.model.Item;
import com.example.RecipeBook.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversionObjService {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    IngredientRepository ingredientRepository;

    public Recipe getRecipeFromConversion(ConversionObj converter) {
        Recipe recipe = new Recipe();
        boolean isIngredient = true;
        StringBuilder instructions = new StringBuilder();
        String[] ingredientsAndInstructions = converter.getContents().split("\\n");
        for (String s : ingredientsAndInstructions) {
            s = s.toLowerCase();
            if (s.contains("instructions") || s.contains("directions"))
                isIngredient = false;
            if (isIngredient && !s.contains("ingredients")) {
                setIngredient(s, recipe);
            } else if (!isIngredient && !s.contains("ingredients")) {
                instructions.append(s);
            }
        }
        recipe.setMethod(instructions.toString());
        return recipe;
    }

    private void setIngredient(String s, Recipe recipe) {
        List<String> items = itemRepository.findAll()
                .stream()
                .map(Item::getName)
                .collect(Collectors.toList());
        items.addAll(ingredientRepository.findAll().stream().map(Ingredient::getSingleName).collect(Collectors.toList()));
        Ingredient i = new Ingredient();
        items.forEach(item -> {
            if (s.toLowerCase().contains(item.toLowerCase()))
                i.setSingleName(item);
        });
        i.setName(s);
        recipe.getIngredients().add(i);
    }
}
