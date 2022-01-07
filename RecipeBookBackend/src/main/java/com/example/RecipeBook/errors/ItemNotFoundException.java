package com.example.RecipeBook.errors;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException() {
        super("Item not found");
    }
}
