package com.example.RecipeBook.item.impl;

import com.example.RecipeBook.item.ItemService;
import com.example.RecipeBook.item.model.Item;
import edu.emory.mathcs.backport.java.util.Collections;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping("/todo")
    public HttpEntity<Collection<Item>> displayShoppingList() {
        try {
            return new ResponseEntity<>(itemService.getShoppingItems(), OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.emptySet(), OK);
        }
    }

    @PostMapping("/todo")
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

    @PostMapping("/todo/add")
    public HttpEntity<Item> updateList(Collection<Item> items) {
        try {
            itemService.updateList(items);
            return new ResponseEntity<>(OK);
        } catch (Exception e) {
            return new ResponseEntity<>(NOT_ACCEPTABLE);
        }
    }
}
