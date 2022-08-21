package com.example.shoppinglist.errors;

public class IncompatibleTypes extends RuntimeException{
    public IncompatibleTypes() {
        super("Types cannot be combined");
    }
}
