package com.example.shoppinglist.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/todo")
    public HttpEntity<Set<ItemDto>> getAllItems() {
        try {
            return new ResponseEntity<>(itemService.getItems(), OK);
        } catch (Exception e) {
            return new ResponseEntity<>(NO_CONTENT);
        }
    }

    @PostMapping("/todo")
    public HttpEntity<ItemDto> addItemToList(@RequestBody ItemDto item) {
        if (item == null) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        try {
            System.out.println("Adding new item to list: " + item);
            itemService.addItem(item);
            return new ResponseEntity<>(OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(METHOD_NOT_ALLOWED);
        }
    }

    @PostMapping("/items/food")
    public HttpEntity<Set<ItemDto>> addFoodItems(@RequestBody Collection<ItemDto> items) {
        itemService.addFoodItems(items);
        return new ResponseEntity<>(OK);
    }

    @PutMapping("/items/food")
    public HttpEntity<Set<ItemDto>> getNonExistingFoodItems(@RequestBody Collection<ItemDto> items) {
        return new ResponseEntity<>(itemService.getNonExistingFoodItems(items), OK);
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
    public HttpEntity<Set<ItemDto>> updateExistingItem(@RequestParam UUID itemId,
                                                       @RequestBody ItemDto itemDto) {
        try {
            System.out.println("Updating: " + itemDto.name);
            itemService.addToExistingItem(itemDto, itemId);
            return getAllItems();
        } catch (Exception e) {
            return new ResponseEntity<>(EXPECTATION_FAILED);
        }
    }
}
