package com.example.RecipeBook.item;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

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
    public HttpEntity<Set<ItemDto>> displayShoppingList() {
        try {
            var items = itemService.getItems();
            return new ResponseEntity<>(items, OK);
        } catch (Exception e) {
            return new ResponseEntity<>(NO_CONTENT);
        }
    }

    @PostMapping("/todo")
    public HttpEntity<Set<ItemDto>> addItemToList(@RequestBody String item) {
        if (item == null) {
            return new ResponseEntity<>(METHOD_NOT_ALLOWED);
        }
        try {
            var list = itemService.addItem(item);
            return new ResponseEntity<>(list, OK);
        } catch (Exception e) {
            return new ResponseEntity<>(METHOD_NOT_ALLOWED);
        }
    }

    @PutMapping("/todo")
    public HttpEntity<ItemDto> updateList(@RequestBody Collection<Item> items) {
        try {
            itemService.updateList(items);
            return new ResponseEntity<>(OK);
        } catch (Exception e) {
            return new ResponseEntity<>(NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/items")
    public HttpEntity<Set<ItemDto>> updateExistingItem(@RequestParam String itemString, @RequestBody ItemDto itemDto) {
        try {
            var item = itemService.addToExistingItem(itemDto, itemString);
            return new ResponseEntity<>(item, OK);
        } catch (Exception e) {
            return new ResponseEntity<>(EXPECTATION_FAILED);
        }
    }

    @GetMapping("/items")
    public HttpEntity<Set<ItemDto>> getItem(@RequestParam String value) {
        return new ResponseEntity<>(itemService.getSimilarItems(value), OK);
    }
}
