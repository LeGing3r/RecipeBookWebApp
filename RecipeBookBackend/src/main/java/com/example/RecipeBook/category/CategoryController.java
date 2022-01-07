package com.example.RecipeBook.category;

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
@RequestMapping("/api")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public HttpEntity<CategoryPage> listCategories(@RequestParam("page") Integer page,
                                                   @RequestParam("size") Integer size) {
        try {
            CategoryPage categories = categoryService.findCategories(page, size);
            return new ResponseEntity<>(categories, OK);
        } catch (Exception e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @PostMapping("/category/delete")
    public HttpEntity<CategoryPage> deleteCategory(@RequestParam UUID catId) {
        if (categoryService.delete(catId)) {
            return new ResponseEntity<>(OK);
        }
        return new ResponseEntity<>(NOT_FOUND);
    }

    @PutMapping("/category/edit")
    public HttpEntity<Category> editCat(@RequestParam UUID catId, @RequestBody Category category) {
        if (categoryService.update(catId, category)) {
            return new ResponseEntity<>(category, OK);
        }
        return new ResponseEntity<>(NOT_FOUND);
    }

    @GetMapping("/category")
    public HttpEntity<Category> getCategory(@RequestParam UUID catId) {
        try {
            Category category = categoryService.findCategoryById(catId);
            return new ResponseEntity<>(category, OK);
        } catch (Exception e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }
}
