package com.example.RecipeBook.service;

import com.example.RecipeBook.dao.CategoryRepository;
import com.example.RecipeBook.dao.RecipeRepository;
import com.example.RecipeBook.model.Category;
import com.example.RecipeBook.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    RecipeRepository recipeRepository;

    public void delete(Integer catId) {
        Category category = categoryRepository.findById(catId).isPresent() ? categoryRepository.findById(catId).get() : null;
        if (category == null)
            return;
        for (Recipe r : recipeRepository.findAll()) {
            r.getCategories().remove(category);
            recipeRepository.save(r);
        }
        categoryRepository.delete(category);
    }
}
