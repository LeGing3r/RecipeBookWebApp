package com.example.RecipeBook.recipe;

import com.example.RecipeBook.errors.RecipeNotFoundException;
import com.example.RecipeBook.recipe.model.Recipe;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class RecipeRepository {
    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;

    private final EntityManager entityManager;

    public RecipeRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        entityManager = entityManagerFactory.createEntityManager();
    }

    public Optional<Recipe> findRecipeById(UUID recipeId) throws SQLException {
        try {
            return entityManager.createQuery("from Recipe where publicId = :id", Recipe.class)
                    .setParameter("id", recipeId)
                    .getResultStream()
                    .findAny();
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Set<Recipe>> findRecipePage(int startPoint, int numberOfRecipes) {
        try {
            return Optional.of(entityManager.createQuery("from Recipe", Recipe.class)
                    .setMaxResults(numberOfRecipes)
                    .setFirstResult(startPoint)
                    .getResultStream()
                    .collect(Collectors.toSet()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public boolean addRecipe(Recipe recipe) {
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            recipe.setPublicId(UUID.randomUUID());
            entityManager.persist(recipe);
            transaction.commit();
            return true;
        } catch (EntityExistsException e) {
            return false;
        }
    }

    public boolean delete(UUID recipeId) {
        try {
            Recipe savedRecipe = findRecipeById(recipeId)
                    .orElseThrow(RecipeNotFoundException::new);
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(savedRecipe);
            transaction.commit();
            return true;
        } catch (IllegalArgumentException | SQLException e) {
            return false;
        }
    }

    public boolean updateRecipe(Recipe recipe) {
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            Recipe savedRecipe = findRecipeById(recipe.getPublicId())
                    .orElseThrow(RecipeNotFoundException::new);
            savedRecipe.mergeWithNewRecipe(recipe);
            transaction.commit();
            return true;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public Optional<Set<Recipe>> findChosen(int startPoint, int numberOfRecipes) {
        try {
            return Optional.of(entityManager.createQuery("from Recipe where chosen = true", Recipe.class)
                    .getResultStream()
                    .collect(Collectors.toSet()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Set<Recipe>> findRecipesByName(String name) {
        try {
            return Optional.of(entityManager.createQuery("from Recipe where name like :name", Recipe.class)
                    .getResultStream()
                    .collect(Collectors.toSet()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

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

    public int getTotalRecipes() {
        return entityManager
                .createQuery("select count(*) from Recipe", Long.class)
                .getSingleResult()
                .intValue();
    }

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
