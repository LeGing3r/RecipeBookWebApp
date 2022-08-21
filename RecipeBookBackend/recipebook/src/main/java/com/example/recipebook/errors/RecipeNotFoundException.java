package com.example.recipebook.errors;

public class RecipeNotFoundException extends RuntimeException{
    public RecipeNotFoundException() {
        super("No recipe found");
    }
}
