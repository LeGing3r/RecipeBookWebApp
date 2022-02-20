package com.example.RecipeBook.errors;

public class IncompatibleTypes extends RuntimeException{
    public IncompatibleTypes() {
        super("Types cannot be combined");
    }
}
