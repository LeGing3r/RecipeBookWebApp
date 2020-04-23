package com.example.RecipeBook.service;

import com.example.RecipeBook.dao.CategoryRepository;
import com.example.RecipeBook.dao.RecipeRepository;
import com.example.RecipeBook.model.Category;
import com.example.RecipeBook.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public void setCategories(Recipe recipe) {
        List<Category> temp = new ArrayList<>();
        for (Category cat : recipe.getCategories())
            for (Category cat1 : categoryRepository.findAll())
                if (cat1.equals(cat))
                    temp.add(cat1);

        recipe.getCategories().removeAll(temp);
        recipe.getCategories().addAll(temp);
        recipe.getCategories().forEach(cat -> categoryRepository.save(cat));
        recipeRepository.save(recipe);
    }

    public void delete(Integer recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).isPresent() ? recipeRepository.findById(recipeId).get() : null;
        if (recipe == null)
            return;
        List<Category> categories = categoryRepository.findAll()
                .stream()
                .filter(category -> category.getRecipes().contains(recipe))
                .collect(Collectors.toList());
        for (Category c : categories) {
            c.getRecipes().remove(recipe);
            categoryRepository.save(c);
        }
        recipeRepository.delete(recipe);
    }
}
