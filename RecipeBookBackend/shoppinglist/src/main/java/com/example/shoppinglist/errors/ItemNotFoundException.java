package com.example.shoppinglist.errors;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException() {
        super("Item not found");
    }
}
