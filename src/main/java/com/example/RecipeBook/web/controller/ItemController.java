package com.example.RecipeBook.web.controller;

import com.example.RecipeBook.dao.IngredientRepository;
import com.example.RecipeBook.dao.ItemRepository;
import com.example.RecipeBook.dao.ItemDtoRepository;
import com.example.RecipeBook.model.Ingredient;
import com.example.RecipeBook.model.Item;
import com.example.RecipeBook.model.Recipe;
import com.example.RecipeBook.model.ItemsDto;
import com.example.RecipeBook.service.ItemService;
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
public class ItemController {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemDtoRepository itemDtoRepository;

    @Autowired
    ItemService itemService;

    @Autowired
    IngredientRepository ingredientRepository;

    @RequestMapping("/items")
    public String findAllItems() {
        ingredientRepository.findAll().forEach(System.out::println);
        return "redirect:/recipes";
    }

    @RequestMapping("/todo")
    public String displayShoppingList(Model model) {
        ItemsDto itemsDto = itemDtoRepository.findFirstItemDto();
        itemsDto.getItems();
        model.addAttribute("cart", itemsDto);
        model.addAttribute("newItem", new Item());
        return "shopping/index";
    }

    //TODO: CLEAN UP CODE SIMPLIFY NAMES, ONLY DISPLAY ITEMS WITH NO CHECK MARKS AFTER REFRESH, AND FINALLY ADD A WAY TO DELETE ITEMS
    @PostMapping("/todo")
    public String updateTodoList(ItemsDto itemsDto) {
        List<Item> items = itemsDto.getItems();
        itemService.updateTodoDto(items);
        return "redirect:/todo";
    }

    @PostMapping("/todo/add")
    public String addToList(@Valid Item item, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("org.springframework.validation.binding.item", result);
            return "redirect:/todo";
        }
        itemService.saveToItemDto(item);
        return "redirect:/todo";
    }

    @PostMapping("/todo/add/{ingredientId}")
    public String incrementQtyToList(Recipe recipe, HttpServletRequest request, @PathVariable Integer ingredientId) {
        Ingredient ingredient = null;
        for (Ingredient i : recipe.getIngredients()) {
            if ((i.getId() != null) && (i.getId().equals(ingredientId)))
                ingredient = i;
        }
        itemService.addToItemDto(ingredient);
        return "redirect:" + request.getHeader("referer");
    }
}
