package com.example.recipebook.service;

import com.example.recipebook.recipe.Recipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends MongoRepository<Recipe, String> {

    Recipe insert(Recipe recipe);

    Optional<Recipe> findById(String recipeId);

    List<Recipe> findAll();

    @Query("{name: {$regex: '.*?0.*', $options: 'i'}}")
    List<Recipe> findByNameContainsIgnoreCase(String name);

    List<Recipe> findByCategories(String name);

    List<Recipe> findByIngredients(String name);

    List<Recipe> findByChosenTrue(Pageable pageable);
}
