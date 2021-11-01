package com.example.RecipeBook.category;

import com.example.RecipeBook.category.model.Category;
import com.example.RecipeBook.category.model.CategoryPage;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CategoryRepository {

    Set<Category> findByCatName(String name);

    int findAmountOfPages(Integer size);

    Optional<CategoryPage> findCategoriesPage(Integer page, Integer size);

    boolean deleteCategory(UUID catId);

    boolean updateCategory(UUID catId, Category category);

    Optional<Category> findCategoryById(UUID catId);

    void saveCategory(Category category);
}
