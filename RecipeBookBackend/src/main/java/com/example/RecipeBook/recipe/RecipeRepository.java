package com.example.RecipeBook.recipe;

import com.example.RecipeBook.utils.QueryType;
import com.example.RecipeBook.errors.RecipeNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.RecipeBook.utils.QueryType.*;

@Repository
public class RecipeRepository {
    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;

    private final EntityManager entityManager;
    private final String WHERE_ID_IS = "where publicId = :id";
    private final String FROM_RECIPES = "select r from Recipe r ";
    private final String WHERE_CHOSEN = "where chosen = true";
    private final String WHERE_NAME_IS = "where name like :name";
    private final String WHERE_CATEGORY_IS = "join r.categories c where c = :name";
    private final String WHERE_INGREDIENT_IS = "join r.ingredients i where i = :name";

    public RecipeRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        entityManager = entityManagerFactory.createEntityManager();
    }

    public Optional<Set<Recipe>> findRecipePage(int startPoint, int numberOfRecipes, boolean chosen) {
        try {
            var query = chosen ? FROM_RECIPES + WHERE_CHOSEN : FROM_RECIPES;
            return Optional.of(entityManager.createQuery(query, Recipe.class)
                    .setMaxResults(numberOfRecipes)
                    .setFirstResult(startPoint)
                    .getResultStream()
                    .collect(Collectors.toSet()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Recipe> findRecipeById(UUID recipeId) {
        try {
            return entityManager.createQuery(FROM_RECIPES + WHERE_ID_IS, Recipe.class)
                    .setParameter("id", recipeId)
                    .getResultStream()
                    .findAny();
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public boolean addRecipe(Recipe recipe) {
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            recipe.publicId = UUID.randomUUID();
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
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean updateRecipe(RecipeDTO recipe) {
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            Recipe savedRecipe = findRecipeById(recipe.getId())
                    .orElseThrow(RecipeNotFoundException::new);
            savedRecipe.mergeWithRecipeDTO(recipe);
            transaction.commit();
            return true;
        } catch (Exception throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public Optional<Set<Recipe>> findRecipesByQuery(String name, QueryType queryType) {
        try {
            var query = switch (queryType) {
                case RECIPE -> FROM_RECIPES + WHERE_NAME_IS;
                case CATEGORY -> FROM_RECIPES + WHERE_CATEGORY_IS;
                case INGREDIENT -> FROM_RECIPES + WHERE_INGREDIENT_IS;
            };
            return Optional.of(entityManager.createQuery(query, Recipe.class)
                    .setParameter("name", name)
                    .getResultStream()
                    .collect(Collectors.toSet()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public int getTotal(QueryType queryType) {
        var recipes = entityManager.createQuery(FROM_RECIPES, Recipe.class)
                .getResultStream()
                .collect(Collectors.toSet());
        if (queryType.equals(CATEGORY)) {
            return recipes.stream()
                    .map(Recipe::getCategories)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet())
                    .size();
        }
        return recipes.size();
    }

    public String getImageLocation(UUID id) {
        try {
            return entityManager
                    .createQuery(FROM_RECIPES + WHERE_ID_IS, Recipe.class)
                    .setParameter("id", id)
                    .getSingleResult()
                    .imageLocation;
        } catch (Exception e) {
            throw new RecipeNotFoundException();
        }
    }

    public void switchChosenOfRecipe(UUID publicId) {
        var recipe = findRecipeById(publicId)
                .orElseThrow(RecipeNotFoundException::new);
        var transaction = entityManager.getTransaction();
        transaction.begin();
        recipe.chosen = !recipe.chosen;
        transaction.commit();
    }

    public void updateRecipe(Recipe recipe, String pathName) {
        var transaction = entityManager.getTransaction();
        transaction.begin();
        recipe.imageLocation = pathName;
        transaction.commit();
    }

    public Set<String> findCategories(int startPoint, int size) {
        return entityManager.createQuery(FROM_RECIPES, Recipe.class)
                .getResultStream()
                .collect(Collectors.toCollection(LinkedHashSet::new))
                .stream()
                .map(Recipe::getCategories)
                .flatMap(Collection::stream)
                .skip(startPoint)
                .limit(size)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
