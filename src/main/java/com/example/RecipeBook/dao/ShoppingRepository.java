package com.example.RecipeBook.dao;

import com.example.RecipeBook.model.ShoppingList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingRepository extends CrudRepository<ShoppingList, Integer> {
}
