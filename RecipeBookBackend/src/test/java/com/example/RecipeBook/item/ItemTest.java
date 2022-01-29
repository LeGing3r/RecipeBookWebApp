package com.example.RecipeBook.item;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

class ItemTest {

    @Test
    public void whenSameItemNameCombine() {
        var staticItem = new StaticItem();
        staticItem.name = "Toilet Paper";
        staticItem.aliases.addAll(Set.of("toilet paper", "Toilet paper", "Toilet Paper"));
        var item = new Item();
        item.name = "toilet paper";
        item.amount = "1";
        item.staticItem = staticItem;
        var secondItem = new Item();
        secondItem.name = "Toilet paper";
        secondItem.amount = "";
        secondItem.staticItem = staticItem;
        item.combineWithItem(secondItem);
        assertEquals(item.amount, "2");
    }

}