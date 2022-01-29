package com.example.RecipeBook.item;

import org.junit.Test;

import java.util.Arrays;

public class DefaultItemServiceTest {

    @Test
    public void givenWhenThen() {
        var y = "one two three";
        Arrays.stream(y.split("([\\W\\s]+)"))
                .filter(w -> !w.equals("two"))
                .forEach(System.out::println);
    }

}