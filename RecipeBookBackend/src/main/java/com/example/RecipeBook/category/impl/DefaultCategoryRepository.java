package com.example.RecipeBook.category.impl;

import com.example.RecipeBook.category.CategoryRepository;
import com.example.RecipeBook.category.model.Category;
import com.example.RecipeBook.category.model.CategoryPage;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public class DefaultCategoryRepository implements CategoryRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    public DefaultCategoryRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Set<Category> findByCatName(String name) {
        return null;
    }

    @Override
    public int findAmountOfPages(Integer size) {
        return 0;
    }

    @Override
    public Optional<CategoryPage> findCategoriesPage(Integer page, Integer size) {
        return Optional.empty();
    }

    @Override
    public boolean deleteCategory(UUID catId) {
        return false;
    }

    @Override
    public boolean updateCategory(UUID catId, Category category) {
        return false;
    }

    @Override
    public Optional<Category> findCategoryById(UUID catId) {
        return Optional.empty();
    }

    @Override
    public void saveCategory(Category category) {

    }
}