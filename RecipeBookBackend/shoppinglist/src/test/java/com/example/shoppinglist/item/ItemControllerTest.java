package com.example.shoppinglist.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ItemControllerTest {

    @Autowired
    private ItemController controller;

    @Test
    public void getItemsAddNewItemAddIdenticalItem() {
        var items = getItems();

        assertFalse(items.isEmpty());

        var item = items.iterator().next();

        var response = controller.addItemToList(item);

        validateResponse(response);

        items = getItems();

        assertTrue(items.contains(item));

        var newItem = items.stream().filter(itemDto -> itemDto.equals(item)).findFirst().orElseThrow(() -> new RuntimeException("Item should not be null"));

        assertNotNull(newItem);
        assertEquals(2, newItem.toItem().actualMeasurement.amount);
    }

    private Set<ItemDto> getItems() {
        var response = controller.getAllItems();

        validateResponse(response);

        var items = response.getBody();

        assertNotNull(items);

        return items;
    }

    private <T> void validateResponse(HttpEntity<T> response) {
        assertEquals(OK, ((ResponseEntity<T>) response).getStatusCode());
    }

}