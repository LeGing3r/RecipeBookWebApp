package com.example.RecipeBook.item.model;

import com.example.RecipeBook.recipe.model.Recipe;

import javax.persistence.*;

@Entity
@Table(name = "Ingredient")
public class IngredientItem extends Item{
    @ManyToOne
    @JoinTable(
            name = "Ingredient_Recipe",
            joinColumns = {@JoinColumn(name = "Ingredient_id")},
            inverseJoinColumns = {@JoinColumn(name = "recipe_id")})
    private Recipe recipe;

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
