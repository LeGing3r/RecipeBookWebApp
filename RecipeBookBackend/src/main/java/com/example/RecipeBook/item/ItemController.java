package com.example.RecipeBook.item;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;
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
    public HttpEntity<Set<ItemDto>> displayShoppingList() {
        try {
            var items = itemService.getItems();
            return new ResponseEntity<>(items, OK);
        } catch (Exception e) {
            return new ResponseEntity<>(NO_CONTENT);
        }
    }

    @PostMapping("/todo")
    public HttpEntity<Set<ItemDto>> addItemToList(@RequestBody ItemDto item) {
        if (item == null) {
            return new ResponseEntity<>(METHOD_NOT_ALLOWED);
        }
        try {
            System.out.println("Adding new item to list: " + item);
            var list = itemService.addItem(item);
            return displayShoppingList();
        } catch (Exception e) {
            return new ResponseEntity<>(METHOD_NOT_ALLOWED);
        }
    }

    @PutMapping("/todo")
    public HttpEntity<ItemDto> updateList(@RequestBody Collection<ItemDto> items) {
        try {
            itemService.updateList(items);
            return new ResponseEntity<>(OK);
        } catch (Exception e) {
            return new ResponseEntity<>(NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/items")
    public HttpEntity<Set<ItemDto>> updateExistingItem(@RequestParam UUID itemId, @RequestBody ItemDto itemDto) {
        try {
            System.out.println("Updating: " + itemDto.name);
            var item = itemService.addToExistingItem(itemDto, itemId);
            return new ResponseEntity<>(item, OK);
        } catch (Exception e) {
            return new ResponseEntity<>(EXPECTATION_FAILED);
        }
    }

    @GetMapping("/items")
    public HttpEntity<Set<ItemDto>> getItemsWithSimilarAlias(@RequestParam String itemName) {
        System.out.println("Getting similar items for " + itemName);
        return new ResponseEntity<>(itemService.getSimilarItems(itemName), OK);
    }
}
