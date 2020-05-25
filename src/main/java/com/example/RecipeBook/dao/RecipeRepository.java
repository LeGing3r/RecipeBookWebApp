package com.example.RecipeBook.dao;

import com.example.RecipeBook.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    @Query("SELECT r FROM Recipe r")
    List<Recipe> findAll();

    @Query("SELECT r FROM Recipe r where r.id=?1")
    Recipe findRecipeById(Integer recipeId);
}
