package com.example.RecipeBook.service;

import com.example.RecipeBook.dao.IngredientRepository;
import com.example.RecipeBook.model.ingredients.Ingredient;
import com.example.RecipeBook.model.Recipe;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IngredientService {
    IngredientRepository ingredientRepository;

    public void saveAll(List<Ingredient> ingredientList) {
        ingredientRepository.saveAll(ingredientList);
    }

    public void saveAll(Recipe recipe) {
        ingredientRepository.saveAll(recipe.getIngredients());
    }

    public boolean setIngredients(Recipe recipe) {
        try {
            recipe.getIngredients().forEach(i -> i.setRecipe(recipe));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void changeNeeded(Integer ingredientId) {
        Ingredient ingredient = ingredientRepository.findById(ingredientId).isPresent() ? ingredientRepository.findById(ingredientId).get() : null;
        if (ingredient == null) {
            throw new IllegalStateException("No ingredient found by that id");
        }
        ingredient.setNeeded(!ingredient.isNeeded());
        ingredientRepository.save(ingredient);
    }
}
