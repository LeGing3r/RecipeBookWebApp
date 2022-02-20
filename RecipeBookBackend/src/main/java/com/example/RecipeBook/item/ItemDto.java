package com.example.RecipeBook.item;

import com.example.RecipeBook.measurement.MeasurementConverter;

import java.util.UUID;

public class ItemDto {

    String measurement;
    String name;
    boolean needed = true;
    UUID id;

    public ItemDto() {
    }

    public ItemDto(Item item) {
        this.name = item.name;
        this.id = item.publicId;
    }

    public ItemDto(String name) {
        this.name = name;
    }

    public ItemDto(String measurement, String name) {
        this.measurement = measurement;
        this.name = name;
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

    public Item toItem() {
        //TODO:Remove whitespaces in frontend
        var item = new Item();
        item.name = name;
        item.measurement = new MeasurementConverter().convertToEntityAttribute(measurement);
        return item;
    }
}
