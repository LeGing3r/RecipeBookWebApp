package com.example.RecipeBook.item;

import java.util.UUID;

public class ItemDto {
    String name;
    boolean needed = true;
    UUID id;

    public ItemDto() {
    }

    public ItemDto(Item item) {
        this.name = item.name;
        this.id = item.publicId;
    }

    public ItemDto(StaticItem item) {
        this.name = item.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNeeded() {
        return needed;
    }

    public void setNeeded(boolean needed) {
        this.needed = needed;
    }
}
