package com.example.RecipeBook.web.controller;

import com.example.RecipeBook.dao.ItemRepository;
import com.example.RecipeBook.dao.TodoRepository;
import com.example.RecipeBook.model.Ingredient;
import com.example.RecipeBook.model.Item;
import com.example.RecipeBook.model.Recipe;
import com.example.RecipeBook.model.TodoItemsDto;
import com.example.RecipeBook.service.TodoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class TodoItemController {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    TodoItemService itemService;

    @RequestMapping("/items")
    public String findAllItems() {
        TodoItemsDto itemsDto = todoRepository.findTodoDto();
        System.out.println(itemsDto.getItems().get(0).getName());
        return "redirect:/recipes";
    }

    @RequestMapping("/todo")
    public String displayShoppingList(Model model) {
        TodoItemsDto itemsDto = todoRepository.findTodoDto();
        itemsDto.getItems();
        model.addAttribute("cart", itemsDto);
        model.addAttribute("newItem", new Item());
        return "shopping/index";
    }

    //TODO: CLEAN UP CODE SIMPLIFY NAMES, ONLY DISPLAY ITEMS WITH NO CHECK MARKS AFTER REFRESH, AND FINALLY ADD A WAY TO DELETE ITEMS
    @PostMapping("/todo")
    public String updateTodoList(TodoItemsDto itemsDto) {
        List<Item> items = itemsDto.getItems();
        for (Item i : items)
            if (!i.isNeeded())
                i.setQty(0);
        items.forEach(item -> item.setTodoItemsDto(itemsDto));
        itemRepository.saveAll(items);
        todoRepository.save(itemsDto);
        return "redirect:/todo";
    }

    @PostMapping("/todo/add")
    public String addToList(@Valid Item item, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.binding.item", result);
            return "redirect:/todo";
        }
        TodoItemsDto itemsDto = todoRepository.findTodoDto();
        List<Item> items = itemsDto.getItems();
        for (Item i : items) {
            if (i.getName().equals(item.getName())) {
                i.increaseQty(item.getQty());
                i.setNeeded(true);
                i.setTodoItemsDto(todoRepository.findTodoDto());
                itemRepository.save(i);
                return "redirect:/todo";
            }
        }
        item.setTodoItemsDto(todoRepository.findTodoDto());
        item.setNeeded(true);
        itemRepository.save(item);
        todoRepository.save(todoRepository.findTodoDto());

        return "redirect:/todo";
    }

    @PostMapping("/todo/add/{ingredientId}")
    public String incrementQtyToList(Recipe recipe, HttpServletRequest request, @PathVariable Integer ingredientId) {
        Ingredient ingredient = null;
        for (Ingredient i : recipe.getIngredients()) {
            if ((i.getId() != null) && (i.getId().equals(ingredientId)))
                ingredient = i;
        }
        itemService.addToTodo(ingredient);
        return "redirect:" + request.getHeader("referer");
    }
}
