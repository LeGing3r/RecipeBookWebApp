package com.example.RecipeBook.item;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ItemDto {

    String name;
    String measurement;
    boolean needed = true;
    UUID id;

    public ItemDto() {
    }

    public ItemDto(Item item) {
        this.name = item.name;
        this.measurement = item.measurement.toString();
        this.needed = item.needed;
        this.id = item.publicId;
    }

    public ItemDto(String measurement, String name) {
        this.measurement = measurement;
        this.name = name;
    }

    public Item toItem() {
        var item = new Item();
        item.name = name;
        item.measurement = new Measurement.MeasurementConverter().convertToEntityAttribute(measurement);
        return item;
    }

    @Override
    public String toString() {
        return measurement + " " + name;
    }
}
