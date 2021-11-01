package com.example.RecipeBook.category;

import com.example.RecipeBook.category.model.Category;
import com.example.RecipeBook.category.model.CategoryPage;
import com.example.RecipeBook.recipe.model.Recipe;

import java.util.Set;
import java.util.UUID;

public interface CategoryService {
    CategoryPage findCategories(Integer page, Integer size);

    boolean delete(UUID catId);

    boolean update(UUID catId, Category category);

    Category findCategoryById(UUID catId);

    Set<Category> findCategoryByRecipeName(String name);

    void deleteRecipeFromCategory(Recipe recipe, Category cat);

    void saveCategory(Category category);

    void setCategories(Recipe recipe);
}
