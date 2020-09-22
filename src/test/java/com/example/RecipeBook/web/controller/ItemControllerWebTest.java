package com.example.RecipeBook.web.controller;

import com.example.RecipeBook.model.Item;
import com.example.RecipeBook.model.ItemsDto;
import com.example.RecipeBook.service.ItemService;
import io.florianlopes.spring.test.web.servlet.request.MockMvcRequestBuilderUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ItemControllerWebTest {
    @Mock
    ItemService itemService;

    MockMvc mockMvc;

    ItemController itemController;

    @BeforeEach
    void setUp() {
        itemController = new ItemController( itemService);

        mockMvc = MockMvcBuilders.standaloneSetup(itemController)
                .build();
    }

    @Test
    void givenItemsDto_postTodo_callsItemService() throws Exception {
        ItemsDto itemsDto = new ItemsDto();
        itemsDto.setId(1);
        itemsDto.setItems(List.of(new Item()));

        mockMvc.perform(MockMvcRequestBuilderUtils.postForm("/todo",itemsDto))
                .andExpect(MockMvcResultMatchers.model().attribute("itemsDto",itemsDto))
                .andExpect(status().is(302));

        verify(itemService).updateTodoDto(itemsDto.getItems());
    }
}