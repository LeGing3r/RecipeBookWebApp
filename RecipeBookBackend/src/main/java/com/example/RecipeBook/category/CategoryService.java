package com.example.RecipeBook.category;

import com.example.RecipeBook.category.model.Category;
import com.example.RecipeBook.category.model.CategoryPage;
import com.example.RecipeBook.errors.CategoryNotFoundException;
import com.example.RecipeBook.recipe.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public CategoryPage findCategories(Integer page, Integer size) {
        return categoryRepository.findCategoriesPage(page, size)
                .orElseThrow(CategoryNotFoundException::new);
    }

    public boolean delete(UUID catId) {
        return categoryRepository.deleteCategory(catId);
    }

    public boolean update(UUID catId, Category category) {
        return categoryRepository.updateCategory(catId, category);
    }

    public Category findCategoryById(UUID catId) {
        return categoryRepository.findCategoryById(catId)
                .orElseThrow(CategoryNotFoundException::new);
    }

    public Set<Category> findCategoryByRecipeName(String name) {
        return null;
    }

    public void deleteRecipeFromCategory(Recipe recipe, Category cat) {

    }

    public void saveCategory(Category category) {

    }

    public void setCategories(Recipe recipe) {
        recipe.getCategories()
                .stream()
                .peek(category -> category.addRecipe(recipe))
                .forEach(categoryRepository::saveCategory);
    }
}
