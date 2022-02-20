package com.example.RecipeBook.errors;

public class ItemsCannotBeCombined extends RuntimeException {
    public ItemsCannotBeCombined() {
        super("Items cannot be combined");
    }
}
