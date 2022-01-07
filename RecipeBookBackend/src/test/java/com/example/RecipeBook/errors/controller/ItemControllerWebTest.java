package com.example.RecipeBook.errors.controller;

import com.example.RecipeBook.item.ItemController;
import com.example.RecipeBook.item.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class ItemControllerWebTest {
    @Mock
    ItemService itemService;

    MockMvc mockMvc;

    ItemController itemController;

    @BeforeEach
    void setUp() {
        itemController = new ItemController(itemService);

        mockMvc = MockMvcBuilders.standaloneSetup(itemController)
                .build();
    }
}