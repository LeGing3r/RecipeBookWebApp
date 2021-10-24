package com.example.RecipeBook.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    @Query("SELECT r FROM Recipe r")
    List<Recipe> findAll();

    Recipe findRecipeById(Integer recipeId);

}
