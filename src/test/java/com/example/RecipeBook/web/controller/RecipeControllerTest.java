package com.example.RecipeBook.web.controller;

import com.example.RecipeBook.model.Recipe;
import com.example.RecipeBook.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {
    @Mock
    Model model;

    @Mock
    RecipeService recipeService;

    RecipeController recipeController;
    MockMvc mockMvc;
    Recipe recipe;

    @BeforeEach
    void setUp() {
        recipeController = new RecipeController(null, recipeService, null, null);

        recipe = new Recipe();
        recipe.setId(1);

        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                .build();
    }

    @Test
    void givenRecipeIdAndModel_showRecipeDetails_callsRecipeRepository() throws Exception {
        //TODO: The problem exists that I can't figure out how to assign a value to recipe so it won't call a null val
        int recipeId = 1;
        String result = recipeController.showRecipeDetails(recipeId, model);
        mockMvc.perform(get(String.format("/recipes/%d",recipeId)))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe", "categories"))
                .andExpect(status().is(302));


        verify(recipeService).findRecipeById(recipeId);
        assertThat(result).isEqualTo("recipe/details");
    }
}