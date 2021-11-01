package com.example.RecipeBook.recipe.model;

import java.util.Set;

public class RecipePage {
    private final Set<Recipe> recipes;
    private final int pagesAmount;
    private final int currentPage;

    public RecipePage(Set<Recipe> recipes, int pagesAmount, int currentPage) {
        this.recipes = recipes;
        this.pagesAmount = pagesAmount;
        this.currentPage = currentPage;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public int getPagesAmount() {
        return pagesAmount;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}
