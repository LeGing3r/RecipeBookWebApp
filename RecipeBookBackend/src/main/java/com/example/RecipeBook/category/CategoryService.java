package com.example.RecipeBook.category;

import com.example.RecipeBook.recipe.RecipeRepository;
import com.example.RecipeBook.recipe.Recipe;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;
    RecipeRepository recipeRepository;

    public void delete(Integer catId) {
        Category category = categoryRepository.findById(catId).isPresent() ? categoryRepository.findById(catId).get() : null;

        if (category == null)
            throw new IllegalStateException("Category not found, please enter a valid id");

        for (Recipe r : recipeRepository.findAll()) {
            r.getCategories().remove(category);
            recipeRepository.save(r);
        }

        categoryRepository.deleteById(catId);
    }

    public Page<Category> findCatPage(Pageable pageable, List<Category> cats) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Category> categoryList;
        if (cats.size() < startItem)
            categoryList = Collections.emptyList();
        else {
            //Either chooses the next x amount of items, or until the end of the list
            int toIndex = Math.min(startItem + pageSize, cats.size());
            categoryList = cats.subList(startItem, toIndex);
        }
        return new PageImpl<>(categoryList, PageRequest.of(currentPage, pageSize), cats.size());

    }

    public Category findById(Integer id) {
        if (categoryRepository.findById(id).isEmpty()) {
            throw new IllegalStateException("No category found by that id");
        }
        return categoryRepository.findById(id).get();
    }

    public List<Category> findAllCatsByName(String query) {
        query = query.toLowerCase().trim();
        String finalQuery = query;
        return categoryRepository.findAll()
                .stream()
                .filter(category -> category.getName().equalsIgnoreCase(finalQuery))
                .collect(Collectors.toList());
    }

    public List<Category> findAll() {
        return categoryRepository.findAll().stream().distinct().collect(Collectors.toList());
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }
}
