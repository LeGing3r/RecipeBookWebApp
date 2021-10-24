package com.example.RecipeBook.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT c FROM Category c")
    List<Category> findAll();

    @Query("SELECT c from Category c WHERE lower(c.name) like %?1%")
    List<Category> findByCatName(String name);

}
