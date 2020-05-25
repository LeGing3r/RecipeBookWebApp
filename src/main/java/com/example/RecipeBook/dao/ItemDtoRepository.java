package com.example.RecipeBook.dao;

import com.example.RecipeBook.model.ItemsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemDtoRepository extends JpaRepository<ItemsDto, Integer> {
    @Query("select t from ItemsDto t where t.id = 1")
    ItemsDto findFirstItemDto();
}
