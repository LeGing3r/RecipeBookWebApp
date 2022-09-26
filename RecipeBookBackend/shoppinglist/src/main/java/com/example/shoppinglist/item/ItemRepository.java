package com.example.shoppinglist.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, Long> {
    String FROM_ITEM = "select i from Item i ";
    String WHERE_NEEDED = " where needed = true";

    @Query(FROM_ITEM + WHERE_NEEDED)
    Set<Item> getTodoItems();

    @Query(value = "SELECT * FROM ITEM i join static_item s on i.static_item_id = s.id join static_item_aliases a on a.static_item_id = s.id where UPPER(a.aliases) LIKE CONCAT('%', UPPER(:name), '%')",
            nativeQuery = true)
    Set<Item> findItemsByStaticItemAliases(String name);

    Item findByPublicId(UUID id);

    Set<Item> findByNameContainsIgnoreCase(String name);

    Set<Item> findByNameIgnoreCase(String name);

}
