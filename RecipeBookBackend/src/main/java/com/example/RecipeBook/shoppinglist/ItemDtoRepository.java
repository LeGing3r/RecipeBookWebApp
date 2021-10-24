package com.example.RecipeBook.shoppinglist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemDtoRepository extends JpaRepository<ItemsDto, Integer> {
    @Query("select t from ItemsDto t where t.id = 1")
    ItemsDto returnItemDto();
}
