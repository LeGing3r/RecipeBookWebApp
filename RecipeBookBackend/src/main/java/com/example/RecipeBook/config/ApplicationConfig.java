package com.example.RecipeBook.config;

import com.example.RecipeBook.category.CategoryRepository;
import com.example.RecipeBook.category.CategoryService;
import com.example.RecipeBook.category.impl.DefaultCategoryRepository;
import com.example.RecipeBook.category.impl.DefaultCategoryService;
import com.example.RecipeBook.ingredient.IngredientService;
import com.example.RecipeBook.ingredient.impl.DefaultIngredientRepository;
import com.example.RecipeBook.ingredient.IngredientRepository;
import com.example.RecipeBook.ingredient.impl.DefaultIngredientService;
import com.example.RecipeBook.recipe.RecipeRepository;
import com.example.RecipeBook.recipe.RecipeService;
import com.example.RecipeBook.recipe.impl.DefaultRecipeRepository;
import com.example.RecipeBook.recipe.impl.DefaultRecipeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class ApplicationConfig {

    @Bean
    public RecipeRepository recipeRepository(EntityManager entityManager) {
        return new DefaultRecipeRepository(entityManager);
    }

    @Bean
    public CategoryRepository categoryRepository(EntityManager entityManager) {
        return new DefaultCategoryRepository(entityManager);
    }

    @Bean
    public IngredientRepository ingredientRepository() {
        return new DefaultIngredientRepository();
    }

    @Bean
    public RecipeService recipeService(RecipeRepository recipeRepository,
                                       CategoryService categoryService,
                                       IngredientService ingredientService) {
        return new DefaultRecipeService(recipeRepository, categoryService, ingredientService);
    }

    @Bean
    public DefaultCategoryService categoryService(CategoryRepository categoryRepository) {
        return new DefaultCategoryService(categoryRepository);
    }

    @Bean
    public DefaultIngredientService ingredientService(IngredientRepository ingredientRepository) {
        return new DefaultIngredientService(ingredientRepository);
    }


}
