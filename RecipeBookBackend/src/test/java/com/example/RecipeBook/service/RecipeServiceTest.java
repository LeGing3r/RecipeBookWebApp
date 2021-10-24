package com.example.RecipeBook.service;

import com.example.RecipeBook.recipe.RecipeRepository;
import com.example.RecipeBook.recipe.Recipe;
import com.example.RecipeBook.recipe.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {
    RecipeService recipeService;
    List<Recipe> recipes;
    Recipe recipe;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        recipeService = new RecipeService(recipeRepository, null, null, null);
        recipes = new ArrayList<>();
    }

    @Test
    void givenEmptyRecipes_findChosen_returnEmptyList() {
        recipe = new Recipe();
        recipe.setId(1);
        recipes.add(recipe);
        when(recipeRepository.findAll()).thenReturn(new ArrayList<>());
        recipeService.findChosen();
    }
}