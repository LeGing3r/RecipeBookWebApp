package com.example.RecipeBook.recipe.impl;

import com.example.RecipeBook.recipe.RecipeRepository;
import com.example.RecipeBook.recipe.model.Recipe;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class DefaultRecipeRepository implements RecipeRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public DefaultRecipeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Recipe> findRecipeById(UUID recipeId) throws SQLException {
        try {
            Recipe recipe = entityManager.createQuery("from Recipe where publicId = :id", Recipe.class)
                    .setParameter("id", recipeId)
                    .getSingleResult();
            return Optional.of(recipe);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Set<Recipe>> findRecipePage(int startPoint, int numberOfRecipes) {
        try {
            return Optional.of(entityManager.createQuery("from Recipe OFFSET :startPoint", Recipe.class)
                    .setParameter("startPoint", startPoint)
                    .setMaxResults(numberOfRecipes)
                    .getResultStream()
                    .collect(Collectors.toSet()));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean saveAndFlush(Recipe recipe) {
        try {
            entityManager.persist(recipe);
            return true;
        } catch (EntityExistsException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Recipe recipe) {
        try {
            entityManager.remove(recipe);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public Optional<Set<Recipe>> findChosen(int startPoint, int numberOfRecipes) {
        try {
            return Optional.of(entityManager.createQuery("from Recipe where chosen = true", Recipe.class)
                    .getResultStream()
                    .collect(Collectors.toSet()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Set<Recipe>> findRecipesByName(String name) {
        try {
            return Optional.of(entityManager.createQuery("from Recipe where name like :name", Recipe.class)
                    .getResultStream()
                    .collect(Collectors.toSet()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Set<Recipe>> findRecipesByCategory(String categoryName) {
        try {
            String qlString = "from Recipe where id in (select recipe_id from recipe_category where category_id in (select id from Category where name like :name";
            return Optional.of(entityManager.createQuery(qlString, Recipe.class)
                    .setParameter("name", categoryName)
                    .getResultStream()
                    .collect(Collectors.toSet()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Set<Recipe>> findRecipesByIngredient(String name) {
        try {
            String qlString = "from Recipe where id in (select recipe_id from recipe_ingredient where ingredient_id in (select id from Ingredient where name like :name";
            return Optional.of(entityManager.createQuery(qlString, Recipe.class)
                    .setParameter("name", name)
                    .getResultStream()
                    .collect(Collectors.toSet()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
