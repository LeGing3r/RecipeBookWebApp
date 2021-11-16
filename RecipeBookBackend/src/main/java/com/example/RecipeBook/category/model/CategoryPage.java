package com.example.RecipeBook.category.model;

import java.util.Set;

public class CategoryPage {
    private final Set<Category> categories;
    private final int pageAmount;
    private final int currentPage;
    private final int pageSize;

    public CategoryPage(Set<Category> categories, int pageAmount, int currentPage, int pageSize) {
        this.categories = categories;
        this.pageAmount = pageAmount;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
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

    public int getPageSize() {
        return pageSize;
    }
}