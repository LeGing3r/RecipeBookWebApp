package com.example.RecipeBook.shoppinglist;

import com.example.RecipeBook.recipe.model.Recipe;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemController {
    ItemService itemService;

    @RequestMapping("/todo")
    public String displayShoppingList(Model model) {
        model.addAttribute("cart", itemService.listOfNeededItems());
        model.addAttribute("newItem", new Item());
        return "shopping/index";
    }

    @PostMapping("/todo")
    public String updateTodoList(ItemsDto itemsDto) {
        if (itemsDto == null) {
            throw new IllegalStateException("itemsDto is mandatory, please provide it");
        }
        Optional.of(itemsDto)
                .map(ItemsDto::getItems)
                .filter(list -> !list.isEmpty())
                .ifPresent(itemService::updateTodoDto);
        return "redirect:/todo";
/*        Optional<ItemsDto> otherOptional = Optional.of(itemsDto);

        List<Item> allItems = Stream.of(itemsDto, itemsDto)
                .map(ItemsDto::getItems)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        itemService.updateTodoDto(allItems);


        Optional.of(itemsDto)
                .flatMap(thisDto -> otherOptional.filter(opt -> opt.getItems().isEmpty()))
                .map(ItemsDto::getItems)
                .filter(list -> !list.isEmpty())
                .ifPresent(itemService::updateTodoDto);*/
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
        if (recipe == null) {
            throw new IllegalStateException("Recipe cannot be null, please use a valid recipe");
        }
        if (ingredientId < 0) {
            throw new IllegalStateException("Ingredient Id cannot be negative");
        }
        itemService.addToItemDto(recipe, ingredientId);
        return "redirect:" + request.getHeader("referer");
    }
}
