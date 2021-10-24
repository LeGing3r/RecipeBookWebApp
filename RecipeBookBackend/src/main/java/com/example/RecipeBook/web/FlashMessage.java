package com.example.RecipeBook.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlashMessage {
    private String message;
    private Status status;

    public enum Status {
        SUCCESS, FAILURE
    }
}
