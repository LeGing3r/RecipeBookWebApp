package com.example.RecipeBook.recipe;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class RecipeDTO {
    String name;
    String imageLocation;
    boolean chosen;
    CookingTime cookingTime;
    NutritionalInfo nutritionalInfo;
    UUID id;
    int portionSize;
    String instructions;
    Set<String> ingredients = new HashSet<>();
    Set<String> categories = new HashSet<>();

    public RecipeDTO() {
    }

    public RecipeDTO(Recipe recipe) {
        this.name = recipe.name;
        this.imageLocation = recipe.imageLocation;
        this.chosen = recipe.chosen;
        this.cookingTime = recipe.cookingTime;
        this.nutritionalInfo = recipe.nutritionalInfo;
        this.id = recipe.publicId;
        this.portionSize = recipe.portionSize;
        this.instructions = recipe.instructions;
        this.ingredients = recipe.ingredients;
        this.categories = recipe.categories;

    }

    public boolean addIngredients(Collection<String> ingredients) {
        return this.ingredients.addAll(ingredients);
    }

    public boolean removeIngredients(Collection<String> ingredients) {
        return this.ingredients.removeAll(ingredients);
    }

    public boolean addCategories(Collection<String> categories) {
        return this.categories.addAll(categories);
    }

    public boolean removeCategories(Collection<String> category) {
        return this.categories.removeAll(category);
    }
}
