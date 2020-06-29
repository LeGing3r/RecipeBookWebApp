package com.example.RecipeBook.web.controller;

import com.example.RecipeBook.dao.CategoryRepository;
import com.example.RecipeBook.model.Category;
import com.example.RecipeBook.service.CategoryService;
import com.example.RecipeBook.web.FlashMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping(value = {"/category", "/categories"})
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("")
    public String listCategories(Model model,
                                 @RequestParam("page") Optional<Integer> page,
                                 @RequestParam("size") Optional<Integer> size) {
        int pageNum = page.orElse(1);
        int pageSize = size.orElse(24);

        List<Category> categories = categoryRepository.findAll().stream().distinct().collect(Collectors.toList());

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


    @RequestMapping("/{catId}/edit")
    public String formEditCat(@PathVariable Integer catId, Model model) {
        Category category = categoryRepository.findById(catId).isPresent() ? categoryRepository.findById(catId).get() : null;
        model.addAttribute("category", category);
        model.addAttribute("action", "/categories");
        return "category/form";
    }

    @RequestMapping(value = "/{catId}/delete", method = RequestMethod.POST)
    public String deleteCategory(@PathVariable Integer catId, Model model) {
        categoryService.delete(catId);
        return "redirect:/categories";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String editCat(@Valid Category category, BindingResult result,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.category", result);
            redirectAttributes.addFlashAttribute("category", category);
            return "redirect:/categories/add";
        }
        categoryRepository.save(category);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Category successfully updated!",
                FlashMessage.Status.SUCCESS));
        return "redirect:/categories";
    }

    @RequestMapping("/{catId}")
    public String returnOneCategory(@PathVariable Integer catId, Model model) {
        Category category = categoryRepository.findById(catId).isPresent() ? categoryRepository.findById(catId).get() : null;
        model.addAttribute("category", category);
        return "category/details";
    }

}
