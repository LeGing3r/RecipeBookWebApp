package com.example.RecipeBook.category;

import com.example.RecipeBook.category.model.Category;
import com.example.RecipeBook.category.model.CategoryPage;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public class CategoryRepository {
    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;

    private final EntityManager entityManager;

    public CategoryRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        entityManager = entityManagerFactory.createEntityManager();
    }

    public Set<Category> findByCatName(String name) {
        return null;
    }

    public int findAmountOfPages(Integer size) {
        return 0;
    }

    public Optional<CategoryPage> findCategoriesPage(Integer page, Integer size) {
        return Optional.empty();
    }

    public boolean deleteCategory(UUID catId) {
        return false;
    }

    public boolean updateCategory(UUID catId, Category category) {
        return false;
    }

    public Optional<Category> findCategoryById(UUID catId) {
        return Optional.empty();
    }

    public void saveCategory(Category category) {

    }
}
