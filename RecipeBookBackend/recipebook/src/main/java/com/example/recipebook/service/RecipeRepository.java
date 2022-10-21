package com.example.recipebook.service;

import com.example.recipebook.recipe.Recipe;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends MongoRepository<Recipe, String> {

    Optional<Recipe> findById(String recipeId);

    List<Recipe> findAll();

    @Aggregation(pipeline = {
            "{'$project':{'id':1,'name':1, 'chosen':1, 'categories':1}}",
            "{'$match':{'chosen': ?0}}",
            "{'$sort': {'name': 1}}",
            "{'$skip': ?2}",
            "{'$limit': ?1}"
    })
    List<Recipe> findPage(boolean chosen, int size, int page);

    @Query("{name: {$regex: '.*?0.*', $options: 'i'}}")
    List<Recipe> findByNameContainsIgnoreCase(String name);

    List<Recipe> findByCategories(String name);

    List<Recipe> findByIngredients(String name);

}
