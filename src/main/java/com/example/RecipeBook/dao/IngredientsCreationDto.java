package com.example.RecipeBook.dao;

import com.example.RecipeBook.model.Ingredient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Data
public class IngredientsCreationDto {
    @Autowired
    IngredientRepository ingredientRepository;

    private List<Ingredient> ingredients = new ArrayList<>();

    public void addIngredient(Ingredient ingredient){
        ingredients.add(ingredient);
        ingredientRepository.save(ingredient);
    }
}
