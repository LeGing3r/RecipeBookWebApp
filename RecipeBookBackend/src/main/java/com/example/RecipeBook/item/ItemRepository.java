package com.example.RecipeBook.item;

import com.example.RecipeBook.errors.ItemNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ItemRepository {
    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;
    private final String NAME_LIKE = "~* '^(:name(es|s)*)$'";
    private final String FROM_ITEM = "from Item";
    private final String FROM_STATIC_ITEM = "from StaticItem";
    private final String WHERE_PUBLIC_ID = "where public_id = :id";

    public ItemRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        entityManager = entityManagerFactory.createEntityManager();
    }

    boolean save(Item item) {
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

    Set<Item> getTodoItems() {
        try {
            return entityManager
                    .createQuery(FROM_ITEM + " where needed = true", Item.class)
                    .getResultStream()
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            return Collections.emptySet();
        }
    }

    Item getItemByUUID(UUID id) {
        return entityManager.createQuery(FROM_ITEM + " " + WHERE_PUBLIC_ID, Item.class)
                .setParameter("id", id)
                .getResultStream()
                .findAny()
                .orElseThrow(ItemNotFoundException::new);
    }

    boolean containsItem(String name) {
        return entityManager.createQuery(FROM_ITEM + " where name " + NAME_LIKE, Item.class)
                .setParameter("name", name)
                .getResultStream()
                .findAny()
                .isPresent();
    }

    Set<StaticItem> getStaticItemsFromAlias(String alias) {
        return entityManager.createQuery(FROM_STATIC_ITEM + " s join s.aliases a where a " + NAME_LIKE, StaticItem.class)
                .setParameter("name", alias)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    Set<Item> getSimilarItemsFromAlias(String alias) {
        return entityManager.createQuery(FROM_ITEM + " where " + NAME_LIKE, Item.class)
                .setParameter("name", alias)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    void removeAll() {
        entityManager.createQuery(FROM_ITEM, Item.class)
                .getResultStream()
                .forEach(entityManager::remove);
    }

    public void addAliasToStaticItem(StaticItem item, String newAlias) {
        var transaction = entityManager.getTransaction();
        transaction.begin();
        item.aliases.add(newAlias);
        transaction.commit();
    }

    public void updateItem(Item item, Item newItem) {
        var transaction = entityManager.getTransaction();
        transaction.begin();
        item.combineWithItem(newItem);
        transaction.commit();
    }
}
