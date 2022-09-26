package com.example.recipebook.recipe;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    String FROM_RECIPES = "SELECT r FROM Recipe r ";
    String WHERE_CHOSEN = "WHERE  r.chosen = true";
    String WHERE_CATEGORY_IS = "JOIN r.categories c WHERE UPPER(c) LIKE CONCAT('%', UPPER(:name), '%')";
    String WHERE_INGREDIENT_IS = "JOIN r.ingredients i WHERE UPPER(i) LIKE CONCAT('%', UPPER(:name), '%')";

    Optional<Recipe> findByPublicId(UUID recipeId);

    List<Recipe> findAll();

    List<Recipe> findByNameContainsIgnoreCase(String name);

    @Query(FROM_RECIPES + WHERE_CATEGORY_IS)
    List<Recipe> findByCategoriesContainsIgnoreCase(String name);

    @Query(FROM_RECIPES + WHERE_INGREDIENT_IS)
    List<Recipe> findByIngredientsContainsIgnoreCase(String name);

    @Query(FROM_RECIPES + WHERE_CHOSEN)
    List<Recipe> findChosenRecipes(Pageable pageable);

    @Query("SELECT COUNT(r) FROM Recipe r")
    int getTotalRecipes();

    @Query("SELECT DISTINCT COUNT(c) FROM Recipe r JOIN r.categories c")
    int getTotalCategories();
}
