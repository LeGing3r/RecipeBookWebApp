package com.example.RecipeBook.service;

import com.example.RecipeBook.dao.CategoryRepository;
import com.example.RecipeBook.dao.IngredientRepository;
import com.example.RecipeBook.dao.RecipeRepository;
import com.example.RecipeBook.model.Category;
import com.example.RecipeBook.model.Ingredient;
import com.example.RecipeBook.model.Recipe;
import edu.emory.mathcs.backport.java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    public void setCategories(Recipe recipe) {
        List<Category> temp = new ArrayList<>();
        for (Category cat : recipe.getCategories())
            for (Category cat1 : categoryRepository.findAll())
                if (cat1.equals(cat))
                    temp.add(cat1);

        recipe.getCategories().removeAll(temp);
        recipe.getCategories().addAll(temp);
        categoryRepository.saveAll(recipe.getCategories());
        recipeRepository.saveAndFlush(recipe);
    }

    public void delete(Integer recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).isPresent() ? recipeRepository.findById(recipeId).get() : null;
        if (recipe == null)
            return;
        List<Category> categories = categoryRepository.findAll()
                .stream()
                .filter(category -> category.getRecipes().contains(recipe))
                .collect(Collectors.toList());
        for (Category c : categories) {
            c.getRecipes().remove(recipe);
            categoryRepository.save(c);
        }
        Paths.get(recipe.getImgPath()).toFile().delete();

        recipeRepository.delete(recipe);
    }

    public void toggleChosen(Integer recipeId) {
        Recipe r = recipeRepository.findRecipeById(recipeId);
        r.setChosen(!(r.isChosen()));
        recipeRepository.save(r);
    }

    public List<Recipe> findRecipesByIngredientName(String name) {
        List<Ingredient> ingredients = ingredientRepository.findByName(name);
        return recipeRepository.findAll()
                .stream()
                .filter(r -> r.containsIngredient(ingredients))
                .collect(Collectors.toList());
    }


    public void saveImgLoc(Recipe recipe, MultipartFile file) {
        try {
            String path = "D:\\Projects\\RecipeBook\\src\\main\\resources\\images\\" + recipe.getId() + ".png";
            File ogFile = Paths.get(path).toFile();
            if (ogFile.exists())
                ogFile.delete();
            file.transferTo(new File(path));

            Path tempPath = Paths.get(String.format("D:\\Projects\\RecipeBook\\target\\classes\\images\\%d.png", recipe.getId()));
            Files.copy(Paths.get(path), tempPath, StandardCopyOption.REPLACE_EXISTING);

            recipe.setImgPath(path);
            recipe.setImgLoc("/images/" + recipe.getId() + ".png");
            recipeRepository.saveAndFlush(recipe);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Page<Recipe> findPages(Pageable pageable, List<Recipe> recipes) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Recipe> newList;
        if (recipes.size() < startItem)
            newList = Collections.emptyList();
        else {
            int toIndex = Math.min(startItem + pageSize, recipes.size());
            newList = recipes.subList(startItem, toIndex);
        }
        Page<Recipe> recipePage =
                new PageImpl<Recipe>(newList, PageRequest.of(currentPage, pageSize), recipes.size());
        return recipePage;
    }
}
