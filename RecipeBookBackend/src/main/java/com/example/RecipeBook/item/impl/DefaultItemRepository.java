package com.example.RecipeBook.item.impl;

import com.example.RecipeBook.item.ItemRepository;
import com.example.RecipeBook.item.model.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class DefaultItemRepository implements ItemRepository {
    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    public DefaultItemRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public Set<Item> getTodoItems() {
        try {
            return entityManager
                    .createQuery("from ShoppingItem", Item.class)
                    .getResultStream()
                    .collect(Collectors.toSet());
        } catch (Exception e) {

            return Collections.emptySet();
        }
    }

    @Override
    public boolean save(Item item) {
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(item);
            transaction.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateItems(Collection<Item> items) {
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            items.forEach(this::updateItem);
            transaction.commit();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void updateItem(Item item) {
        if (item.isNeeded()) {
            entityManager.persist(item);
        } else {
            entityManager.remove(item);
        }
    }
}
