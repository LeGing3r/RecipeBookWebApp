package com.example.RecipeBook.dao;

import com.example.RecipeBook.model.Ingredient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Integer> {
    @Query("SELECT l FROM Ingredient l")
    List<Ingredient> findAll();
}
