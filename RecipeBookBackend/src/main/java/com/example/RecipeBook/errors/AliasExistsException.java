package com.example.RecipeBook.errors;

public class AliasExistsException extends RuntimeException {
    public AliasExistsException(){
        super("Some item already has this alias");
    }
}
