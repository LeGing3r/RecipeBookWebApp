package com.example.RecipeBook.item;

import com.example.RecipeBook.errors.AliasExistsException;
import com.example.RecipeBook.errors.ItemNotFoundException;
import com.example.RecipeBook.item.model.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ItemRepository {
    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    public ItemRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        entityManager = entityManagerFactory.createEntityManager();
    }

    public Set<Item> getTodoItems() {
        try {
            return entityManager
                    .createQuery("from Item where needed = true", Item.class)
                    .getResultStream()
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            return Collections.emptySet();
        }
    }

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

    public Optional<Item> getItemWithAlias(String newAlias) {
        return entityManager.createQuery("from Item where id in (SELECT Ingredient_Id from Item_Alias where alias = :name", Item.class)
                .setParameter("name", newAlias)
                .getResultStream()
                .findAny();
    }

    public Item getItemByUUID(UUID id) {
        return entityManager.createQuery("from Item where public_id = :id", Item.class)
                .setParameter("id", id)
                .getResultStream()
                .findAny()
                .orElseThrow(ItemNotFoundException::new);
    }

    public void addAliasToItem(Item item, String newAlias) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        if(!item.addAlias(newAlias)){
            throw new AliasExistsException();
        }
        entityManager.persist(item);
        transaction.commit();
    }

    public boolean containsItem(String name) {
        return entityManager.createQuery("from Item where name ~* '^(:name(es|s)*)$'", Item.class)
                .setParameter("name", name)
                .getResultStream()
                .findAny()
                .isPresent();
    }

    public boolean containsItem(UUID uuid) {
        return entityManager.createQuery("from Item where publicId = :id", Item.class)
                .setParameter("id", uuid)
                .getResultStream()
                .findAny()
                .isPresent();
    }

    private void updateItem(Item item) {
        if (item.isNeeded()) {
            entityManager.persist(item);
        } else {
            entityManager.remove(item);
        }
    }
}
