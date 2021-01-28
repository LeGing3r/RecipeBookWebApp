package com.example.RecipeBook.service;

import com.example.RecipeBook.dao.CategoryRepository;
import com.example.RecipeBook.dao.IngredientRepository;
import com.example.RecipeBook.dao.RecipeRepository;
import com.example.RecipeBook.model.Category;
import com.example.RecipeBook.model.Ingredient;
import com.example.RecipeBook.model.Recipe;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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

import static com.example.RecipeBook.StaticStrings.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RecipeService {
    RecipeRepository recipeRepository;
    CategoryRepository categoryRepository;
    IngredientRepository ingredientRepository;
    IngredientService ingredientService;

    public void setCategories(Recipe recipe) {
        List<Category> temp = new ArrayList<>();
        for (Category cat : recipe.getCategories())
            for (Category cat1 : categoryRepository.findAll())
                if (cat1.equals(cat))
                    temp.add(cat1);

        recipe.getCategories().removeAll(temp);
        recipe.getCategories().addAll(temp);
        categoryRepository.saveAll(recipe.getCategories());
        saveRecipe(recipe);
    }

    private void saveRecipe(Recipe recipe) {
        recipeRepository.saveAndFlush(recipe);
    }

    public void delete(Integer recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).isPresent() ? recipeRepository.findById(recipeId).get() : null;
        if (recipe == null)
            throw new IllegalStateException("No recipe with given id");
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
        r.switchChosen();
        saveRecipe(r);
    }

    public List<Recipe> findRecipesByIngredientName(String name) {
        List<Ingredient> ingredients = ingredientRepository.findByName(name);
        return recipeRepository.findAll()
                .stream()
                .filter(r -> r.containsIngredient(ingredients))
                .collect(Collectors.toList());
    }

    public void setImgLoc(Recipe recipe, MultipartFile file) throws IOException {

        String path = IMG_LOC + recipe.getId() + PNG;
        File ogFile = Paths.get(path).toFile();
        if (ogFile.exists())
            ogFile.delete();
        file.transferTo(new File(path));
//            ImageIO.write(ImageIO.read(Paths.get(path).toFile()), "png", new File(path));

        Path tempPath = Paths.get(IMG_TEMP_LOC + recipe.getId() + PNG);
        Files.copy(Paths.get(path), tempPath, StandardCopyOption.REPLACE_EXISTING);

        recipe.setImgLoc("/images/" + recipe.getId() + PNG);
        recipe.setImgPath(path);

    }

    public Page<Recipe> findPages(Pageable pageable, List<Recipe> recipes) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Recipe> newList;
        if (recipes.size() < startItem)
            newList = new ArrayList<>();
        else {
            int toIndex = Math.min(startItem + pageSize, recipes.size());
            newList = recipes.subList(startItem, toIndex);
        }
        return new PageImpl<>(newList, PageRequest.of(currentPage, pageSize), recipes.size());
    }

    public Recipe findRecipeById(Integer recipeId) {
        return recipeRepository.findRecipeById(recipeId);
    }

    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    public List<Recipe> findChosen() {
        return recipeRepository.findAll()
                .stream()
                .filter(Recipe::isChosen)
                .collect(Collectors.toList());
    }

    public List<Recipe> findAllByQuery(String query) {
        return recipeRepository.findAll()
                .stream()
                .filter(r -> r.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Recipe> findAllRecipesByCatName(String query) {
        query = query.toLowerCase().trim();
        String finalQuery = query;
        List<Recipe> recipes = new ArrayList<>();
        categoryRepository.findAll()
                .stream()
                .filter(category -> category.getName().equalsIgnoreCase(finalQuery))
                .forEach(category -> recipes.addAll(category.getRecipes()));
        return recipes;
    }

    public boolean updateRecipe(Recipe recipe,
                                MultipartFile file) {
        try {
            ingredientService.setIngredients(recipe);
            setCategories(recipe);
            ingredientService.saveAll(recipe.getIngredients());
            setImgLoc(recipe, file);
        } catch (Exception e) {
            return false;
        }
        saveRecipe(recipe);
        return true;
    }

    public boolean addRecipe(Recipe recipe, MultipartFile file) {
        try {
            recipe.getIngredients().forEach(ingredient -> ingredient.setRecipe(recipe));
            setCategories(recipe);
            ingredientService.saveAll(recipe);
            setImgLoc(recipe, file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        saveRecipe(recipe);
        return true;
    }
}
