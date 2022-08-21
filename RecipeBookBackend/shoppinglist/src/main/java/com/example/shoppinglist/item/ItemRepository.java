package com.example.shoppinglist.item;

import com.example.shoppinglist.errors.ItemNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ItemRepository {
    @PersistenceUnit
    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;
    private final String NAME_LIKE = "LIKE CONCAT('%',:name,'%')";
    private final String FROM_ITEM = "select i from Item i ";
    private final String FROM_STATIC_ITEM = "select s from StaticItem s ";
    private final String WHERE_PUBLIC_ID = " where public_id = :id";

    public ItemRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        entityManager = entityManagerFactory.createEntityManager();
    }

    Set<Item> getTodoItems() {
        try {
            return entityManager
                    .createQuery(FROM_ITEM + " where needed = true", Item.class)
                    .getResultStream()
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptySet();
        }
    }

    void save(Item item) {
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            if (item.id == null) {
                item.publicId = UUID.randomUUID();
            }
            entityManager.persist(item);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void removeItem(UUID uuid) {
        try {
            var transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.createQuery(FROM_ITEM + WHERE_PUBLIC_ID, Item.class)
                    .setParameter("id", uuid)
                    .getResultStream()
                    .forEach(entityManager::remove);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Item getItemByUUID(UUID id) {
        return entityManager.createQuery(FROM_ITEM + " " + WHERE_PUBLIC_ID, Item.class)
                .setParameter("id", id)
                .getResultStream()
                .findAny()
                .orElseThrow(ItemNotFoundException::new);
    }

    Set<StaticItem> getStaticItemsFromAlias(String alias) {
        return entityManager.createQuery(FROM_STATIC_ITEM + " join s.aliases a where a like :name", StaticItem.class)
                .setParameter("name", alias)
                .getResultStream()
                .collect(Collectors.toSet());
    }

    StaticItem getStaticItemByName(String name) {
        try {
            if (name.endsWith("s")) {
                name = name.substring(0, name.length() - 2);
            }
            return entityManager.createQuery(FROM_STATIC_ITEM + " where name " + NAME_LIKE, StaticItem.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException | EntityNotFoundException e) {
            try {
                return entityManager.createQuery(FROM_STATIC_ITEM + " join s.aliases a where a " + NAME_LIKE, StaticItem.class)
                        .setParameter("name", name)
                        .getSingleResult();
            } catch (NoResultException | EntityNotFoundException e1) {
                return null;
            }
        }
    }

    Set<Item> getSimilarItemsFromAlias(String alias) {
        Set<Item> similarItems = new HashSet<>();
        try {
            var items = entityManager.createQuery(FROM_ITEM + " where name " + NAME_LIKE, Item.class)
                    .setParameter("name", alias)
                    .getResultList();
            similarItems.addAll(items);
        } catch (NoResultException e) {
        }
        try {
            var itemsFromStaticItems = entityManager.createQuery(
                            FROM_ITEM + " where i.staticItem.id in ( select s.id from StaticItem s join s.aliases a where a " + NAME_LIKE + ")", Item.class)
                    .setParameter("name", alias)
                    .getResultList();
            similarItems.addAll(itemsFromStaticItems);
        } catch (NoResultException e) {
        }
        return similarItems;


    }
}
