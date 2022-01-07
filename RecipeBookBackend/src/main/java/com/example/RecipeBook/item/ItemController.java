package com.example.RecipeBook.item;

import com.example.RecipeBook.item.model.Item;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/todo")
    public HttpEntity<Collection<Item>> displayShoppingList() {
        try {
            Collection<Item> items = itemService.getItems();
            return new ResponseEntity<>(items, OK);
        } catch (Exception e) {
            return new ResponseEntity<>(NO_CONTENT);
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

    @PutMapping("/todo")
    public HttpEntity<Item> updateList(@RequestBody Collection<Item> items) {
        try {
            itemService.updateList(items);
            return new ResponseEntity<>(OK);
        } catch (Exception e) {
            return new ResponseEntity<>(NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/items")
    public HttpEntity<Item> addAliasToItem(@RequestParam UUID id, @RequestParam String newAlias) {
        try {
            var item = itemService.addAliasToItem(id, newAlias);
            return new ResponseEntity<>(item, OK);
        } catch (Exception e) {
            return new ResponseEntity<>(EXPECTATION_FAILED);
        }
    }

    @PostMapping("/item")
    public HttpEntity<Item> getItem(@RequestParam UUID id) {
        return new ResponseEntity<>(itemService.getItem(id), OK);
    }
}
