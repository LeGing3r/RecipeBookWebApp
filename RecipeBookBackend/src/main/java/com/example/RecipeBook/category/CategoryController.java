package com.example.RecipeBook.category;

import com.example.RecipeBook.errors.FlashMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = {"/category", "/categories"})
public class CategoryController {
    CategoryService categoryService;

    @GetMapping("")
    public String listCategories(Model model,
                                 @RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size") Optional<Integer> size) {
        int pageNum = page.orElse(1);
        int pageSize = size.orElse(24);

        List<Category> categories = categoryService.findAll();

        Page<Category> catPage = categoryService.findCatPage(PageRequest.of(pageNum - 1, pageSize), categories);
        int totalPages = catPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("currentPage", pageNum);
        }

        model.addAttribute("catPage", catPage);
        return "category/index";
    }


    @GetMapping("/{catId}/edit")
    public String formEditCat(@PathVariable Integer catId, Model model) {
        model.addAttribute("category", categoryService.findById(catId));
        model.addAttribute("action", "/categories");
        return "category/form";
    }

    @PostMapping(value = "/{catId}/delete")
    public String deleteCategory(@PathVariable Integer catId, Model model) {
        categoryService.delete(catId);
        return "redirect:/categories";
    }

    @PostMapping(value = "")
    public String editCat(@Valid Category category, BindingResult result,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.category", result);
            redirectAttributes.addFlashAttribute("category", category);
            return "redirect:/categories/add";
        }
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Category successfully updated!",
                FlashMessage.Status.SUCCESS));
        return "redirect:/categories";
    }

    @GetMapping("/{catId}")
    public String returnOneCategory(@PathVariable Integer catId, Model model) {
        model.addAttribute("category", categoryService.findById(catId));
        return "category/details";
    }

}
