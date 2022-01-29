package com.example.RecipeBook.errors;

public class ItemsCannotBeCombined extends RuntimeException {
    public ItemsCannotBeCombined() {
        super("Items canot be combined");
    }
}
