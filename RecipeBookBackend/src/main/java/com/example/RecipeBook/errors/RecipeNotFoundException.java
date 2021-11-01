package com.example.RecipeBook.errors;

public class RecipeNotFoundException extends RuntimeException{
    public RecipeNotFoundException() {
        super("No recipe found");
    }
}
