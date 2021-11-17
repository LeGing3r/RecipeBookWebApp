package com.example.RecipeBook.item.model.item;

import com.example.RecipeBook.item.model.Measurement;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "ShoppingItem")
public class ShoppingItem extends Item {
    private boolean needed = true;
    private Measurement defaultMeasurement;
    private UUID publicId;

    public UUID getPublicId() {
        return publicId;
    }

    public void setPublicId(UUID publicId) {
        this.publicId = publicId;
    }

    public boolean isNeeded() {
        return needed;
    }

    public void setNeeded(boolean needed) {
        this.needed = needed;
    }

    public Measurement getDefaultMeasurement() {
        return defaultMeasurement;
    }

    public void setDefaultMeasurement(Measurement defaultMeasurement) {
        this.defaultMeasurement = defaultMeasurement;
    }
}
