package com.example.RecipeBook.service;

import com.example.RecipeBook.dao.ItemRepository;
import com.example.RecipeBook.dao.TodoRepository;
import com.example.RecipeBook.model.Ingredient;
import com.example.RecipeBook.model.Item;
import com.example.RecipeBook.model.TodoItemsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoItemService {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    ItemRepository itemRepository;

    public void addToTodo(Ingredient i) {
        List<Item> items = todoRepository.findTodoDto().getItems();
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(i.getSingleName())) {
                item.increaseQty(i.getNeededQty());
                itemRepository.save(item);
                todoRepository.save(todoRepository.findTodoDto());
                return;
            }
        }
        Item item = new Item();
        item.setName(i.getSingleName());
        item.setQty(i.getNeededQty());
        item.setTodoItemsDto(todoRepository.findTodoDto());
        itemRepository.save(item);
        todoRepository.save(todoRepository.findTodoDto());
    }

    public void save(TodoItemsDto itemsDto) {
        itemRepository.saveAll(itemsDto.getItems());
        todoRepository.save(itemsDto);
    }
}
