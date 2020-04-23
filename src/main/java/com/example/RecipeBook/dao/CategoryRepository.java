package com.example.RecipeBook.dao;

import com.example.RecipeBook.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    @Query("SELECT c FROM Category c")
    List<Category> findAll();
}
