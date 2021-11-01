package com.example.RecipeBook.service;

import com.example.RecipeBook.category.CategoryRepository;
import com.example.RecipeBook.category.impl.DefaultCategoryService;
import com.example.RecipeBook.category.model.Category;
import com.example.RecipeBook.recipe.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    RecipeRepository recipeRepository;

    DefaultCategoryService categoryService;

    Category category;

    @BeforeEach
    void setUp() {
        categoryService = new DefaultCategoryService(categoryRepository);
        category = new Category();
        category.setId(1);
    }

    @Test
    void givenCatId_delete_categoryRepositoryCallsDelete() {
        int catId = 1;

    }

    @Test
    void givenBadOrNullCatId_delete_illegalStateExceptionIsThrown(){
        assertThatThrownBy(()-> categoryService.delete(null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Category not found, please enter a valid id");
    }


}