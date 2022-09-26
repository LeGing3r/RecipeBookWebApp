package com.example.shoppinglist.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StaticItemRepository extends JpaRepository<StaticItem, Long> {
    String FROM_STATIC_ITEM = "select s from StaticItem s ";
    String JOIN_ALIASES = "JOIN s.aliases a WHERE UPPER(a) LIKE CONCAT('%', UPPER(:name) ,'%')";

    @Query(FROM_STATIC_ITEM + JOIN_ALIASES)
    List<StaticItem> findWhereAliasContains(String name);
}
