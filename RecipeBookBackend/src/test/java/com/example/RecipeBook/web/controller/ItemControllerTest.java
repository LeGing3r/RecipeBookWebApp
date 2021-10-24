package com.example.RecipeBook.web.controller;

import com.example.RecipeBook.dao.IngredientRepository;
import com.example.RecipeBook.dao.ItemDtoRepository;
import com.example.RecipeBook.model.Item;
import com.example.RecipeBook.model.ItemsDto;
import com.example.RecipeBook.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest
{
    @Mock
    ItemService itemService;

    @Mock
    ItemsDto itemsDto;

    @Mock
    ItemDtoRepository itemDtoRepository;

    @Mock
    IngredientRepository ingredientRepository;

    @Mock
    Item item;

    ItemController itemController;
    List<Item> itemList;

    @BeforeEach
    void setUp() {
        itemList = new ArrayList<>();
        itemController = new ItemController(itemService);
    }

    @Test
    void givenListOfItems_updateTodoList_itemServiceIsCalled() {
        when(itemsDto.getItems()).thenReturn(itemList);
        itemList.add(item);
        String result = itemController.updateTodoList(itemsDto);

        verify(itemService).updateTodoDto(itemList);
        assertThat(itemList)
                .hasSize(1)
                .containsExactly(item);
        assertThat(result).isEqualTo("redirect:/todo");
    }

    @Test
    void givenNullItemsDto_updateTodoList_illegalStateExceptionIsThrown(){
        assertThatThrownBy(() -> itemController.updateTodoList(null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("itemsDto is mandatory, please provide it");
    }

    @Test
    void givenEmptyItemsList_updateTodoList_itemServiceNotCalled(){
        when(itemsDto.getItems()).thenReturn(new ArrayList<>());
        String result = itemController.updateTodoList(itemsDto);

        verifyNoInteractions(itemService);
        assertThat(result).isEqualTo("redirect:/todo");
    }
}