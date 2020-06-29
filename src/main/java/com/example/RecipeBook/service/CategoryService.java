package com.example.RecipeBook.service;

import com.example.RecipeBook.dao.CategoryRepository;
import com.example.RecipeBook.dao.RecipeRepository;
import com.example.RecipeBook.model.Category;
import com.example.RecipeBook.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    RecipeRepository recipeRepository;

    public void delete(Integer catId) {
        Category category = categoryRepository.findById(catId).isPresent() ? categoryRepository.findById(catId).get() : null;
        if (category == null)
            return;
        for (Recipe r : recipeRepository.findAll()) {
            r.getCategories().remove(category);
            recipeRepository.save(r);
        }
        categoryRepository.delete(category);
    }

    public Page<Category> findCatPage(Pageable pageable, List<Category> cats) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Category> categoryList;
        if (cats.size() < startItem)
            categoryList = Collections.emptyList();
        else {
            int toIndex = Math.min(startItem + pageSize, cats.size());
            categoryList = cats.subList(startItem, toIndex);
        }
        return new PageImpl<>(categoryList, PageRequest.of(currentPage, pageSize), cats.size());

    }
}
