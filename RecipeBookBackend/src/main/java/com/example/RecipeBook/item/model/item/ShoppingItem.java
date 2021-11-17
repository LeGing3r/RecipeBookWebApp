package com.example.RecipeBook.item.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ShoppingItem")
public class ShoppingItem extends Item {
    private boolean needed = true;

    public boolean isNeeded() {
        return needed;
    }

    public void setNeeded(boolean needed) {
        this.needed = needed;
    }
}
