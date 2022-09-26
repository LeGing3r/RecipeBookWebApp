package com.example.recipebook.recipe;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
public class RecipeDTO {
    String name;
    boolean chosen;
    CookingTime cookingTime;
    NutritionalInfo nutritionalInfo;
    UUID id;
    int portionSize;
    String instructions;
    Set<String> ingredients;
    Set<String> categories;

    public RecipeDTO(Recipe recipe) {
        this.name = recipe.name;
        this.chosen = recipe.chosen;
        this.cookingTime = recipe.cookingTime;
        this.nutritionalInfo = recipe.nutritionalInfo;
        this.id = recipe.publicId;
        this.portionSize = recipe.portionSize;
        this.instructions = recipe.instructions;
        this.ingredients = recipe.ingredients;
        this.categories = recipe.categories;
    }
}
