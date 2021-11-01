package com.example.RecipeBook.category.impl;

import com.example.RecipeBook.category.CategoryService;
import com.example.RecipeBook.category.model.Category;
import com.example.RecipeBook.category.model.CategoryPage;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public HttpEntity<CategoryPage> listCategories(@RequestParam("page") Integer page,
                                                   @RequestParam("size") Integer size) {
        try {
            CategoryPage categories = categoryService.findCategories(page, size);
            return new ResponseEntity<>(categories, OK);
        } catch (Exception e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @PostMapping("/{catId}/delete")
    public HttpEntity<CategoryPage> deleteCategory(@PathVariable UUID catId) {
        if (categoryService.delete(catId)) {
            return new ResponseEntity<>(OK);
        }
        return new ResponseEntity<>(NOT_FOUND);
    }

    @PutMapping("/{catId}")
    public HttpEntity<Category> editCat(@PathVariable UUID catId,
                                        Category category) {
        if (categoryService.update(catId, category)) {
            return new ResponseEntity<>(category, OK);
        }
        return new ResponseEntity<>(NOT_FOUND);
    }

    @GetMapping("/{catId}")
    public HttpEntity<Category> getCategory(@PathVariable UUID catId) {
        try {
            Category category = categoryService.findCategoryById(catId);
            return new ResponseEntity<>(category, OK);
        } catch (Exception e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }
}
