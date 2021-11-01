package com.example.RecipeBook.category.impl;

import com.example.RecipeBook.category.CategoryService;
import com.example.RecipeBook.category.CategoryRepository;
import com.example.RecipeBook.category.model.Category;
import com.example.RecipeBook.category.model.CategoryPage;
import com.example.RecipeBook.errors.CategoryNotFoundException;
import com.example.RecipeBook.recipe.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class DefaultCategoryService implements CategoryService {
    private final CategoryRepository categoryRepository;

    public DefaultCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public CategoryPage findCategories(Integer page, Integer size) {
        return categoryRepository.findCategoriesPage(page, size)
                .orElseThrow(CategoryNotFoundException::new);
    }

    @Override
    public boolean delete(UUID catId) {
        return categoryRepository.deleteCategory(catId);
    }

    @Override
    public boolean update(UUID catId, Category category) {
        return categoryRepository.updateCategory(catId, category);
    }

    @Override
    public Category findCategoryById(UUID catId) {
        return categoryRepository.findCategoryById(catId)
                .orElseThrow(CategoryNotFoundException::new);
    }

    @Override
    public Set<Category> findCategoryByRecipeName(String name) {
        return null;
    }

    @Override
    public void deleteRecipeFromCategory(Recipe recipe, Category cat) {

    }

    @Override
    public void saveCategory(Category category) {

    }

    @Override
    public void setCategories(Recipe recipe) {
        recipe.getCategories()
                .stream()
                .peek(category -> category.addRecipe(recipe))
                .forEach(categoryRepository::saveCategory);
    }
}
