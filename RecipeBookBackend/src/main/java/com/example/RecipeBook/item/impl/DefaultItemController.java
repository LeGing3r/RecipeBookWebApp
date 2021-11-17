package com.example.RecipeBook.item.impl;

import com.example.RecipeBook.item.ItemService;
import com.example.RecipeBook.item.model.Item;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/todo")
public class DefaultItemController {
    private final ItemService itemService;

    public DefaultItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public HttpEntity<Collection<Item>> displayShoppingList() {
        try {
            Collection<Item> items = itemService.getItems();
            return new ResponseEntity<>(items, OK);
        } catch (Exception e) {
            return new ResponseEntity<>(NO_CONTENT);
        }
    }

    @PostMapping
    public HttpEntity<Item> addItemToList(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("No item added!");
        }
        try {
            itemService.addItem(item);
            return new ResponseEntity<>(OK);
        } catch (Exception e) {
            return new ResponseEntity<>(NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/add")
    public HttpEntity<Item> updateList(Collection<Item> items) {
        try {
            itemService.updateList(items);
            return new ResponseEntity<>(OK);
        } catch (Exception e) {
            return new ResponseEntity<>(NOT_ACCEPTABLE);
        }
    }
}
