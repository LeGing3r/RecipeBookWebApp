package com.example.RecipeBook.dao;

import com.example.RecipeBook.model.Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
    @Query("SELECT r FROM Recipe r")
    List<Recipe> findAll();
}
