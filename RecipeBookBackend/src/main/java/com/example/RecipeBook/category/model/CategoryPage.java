package com.example.RecipeBook.category.model;

import java.util.Set;

public class CategoryPage {
    private Set<Category> categories;
    private int pageAmount;
    private int currentPage;

    public CategoryPage(Set<Category> categories, int pageAmount, int currentPage) {
        this.categories = categories;
        this.pageAmount = pageAmount;
        this.currentPage = currentPage;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public int getPageAmount() {
        return pageAmount;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}
