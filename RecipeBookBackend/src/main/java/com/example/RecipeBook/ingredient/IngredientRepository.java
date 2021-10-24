package com.example.RecipeBook.ingredient;

import com.example.RecipeBook.ingredient.ingredients.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    @Query("SELECT l FROM Ingredient l")
    List<Ingredient> findAll();

    @Query("SELECT i from Ingredient i WHERE lower(i.name) like %?1%")
    List<Ingredient> findByName(String name);

}
