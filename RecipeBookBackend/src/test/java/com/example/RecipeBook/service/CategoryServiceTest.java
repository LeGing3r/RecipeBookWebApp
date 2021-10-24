package com.example.RecipeBook.service;

import com.example.RecipeBook.category.CategoryRepository;
import com.example.RecipeBook.category.CategoryService;
import com.example.RecipeBook.recipe.RecipeRepository;
import com.example.RecipeBook.category.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    RecipeRepository recipeRepository;

    CategoryService categoryService;

    Category category;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryService(categoryRepository, recipeRepository);
        category = new Category();
        category.setId(1);
    }

    @Test
    void givenCatId_delete_categoryRepositoryCallsDelete() {
        int catId = 1;

        when(categoryRepository.findById(catId)).thenReturn(Optional.of(category));
        when(recipeRepository.findAll()).thenReturn(new ArrayList<>());
        categoryService.delete(catId);

        verify(categoryRepository).deleteById(catId);
    }

    @Test
    void givenBadOrNullCatId_delete_illegalStateExceptionIsThrown(){
        assertThatThrownBy(()-> categoryService.delete(null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Category not found, please enter a valid id");
    }


}