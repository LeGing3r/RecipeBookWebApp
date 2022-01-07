package com.example.RecipeBook.recipe.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class RecipePage {
    private final Set<RecipePageElement> recipes;
    private final int pagesAmount;
    private final int currentPage;
    private final int pageSize;

    public RecipePage(Set<RecipePageElement> recipes, int pagesAmount, int currentPage, int pageSize) {
        this.recipes = new LinkedHashSet<>(recipes);
        this.pagesAmount = pagesAmount;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public Set<RecipePageElement> getRecipes() {
        return recipes;
    }

    public int getPagesAmount() {
        return pagesAmount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }
}
