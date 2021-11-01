package com.example.RecipeBook.service;

import com.example.RecipeBook.recipe.RecipeRepository;
import com.example.RecipeBook.recipe.impl.DefaultRecipeService;
import com.example.RecipeBook.recipe.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {
    private DefaultRecipeService recipeService;
    private List<Recipe> recipes;
    private Recipe recipe;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        recipeService = new DefaultRecipeService(recipeRepository, null, null);
        recipes = new ArrayList<>();
    }
}