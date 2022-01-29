package com.example.RecipeBook.item;

import com.example.RecipeBook.measurement.Measurement;
import com.example.RecipeBook.measurement.MeasurementUtils;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

import static com.example.RecipeBook.NumberUtils.findNumberInString;

@Entity
@DiscriminatorColumn(
        name = "discriminator",
        discriminatorType = DiscriminatorType.STRING
)
@DiscriminatorValue("I")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    UUID publicId;
    String name;
    String amount;
    Boolean needed = true;
    String stringValue;
    StaticItem staticItem;

    public Item() {
    }

    Item(String name) {
        this.name = name;
    }

    public UUID getPublicId() {
        return publicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return (id != null && item.id != null && id.equals(item.id)) || this.toString().equals(o.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name;
    }

    void combineWithItem(Item newItem) {

    }

    public static class ItemConverter {
        public static Item convertFromString(String itemString) {
            boolean isFoodIem = false;
            for (Measurement m : Measurement.values()) {
                for (String s : m.getAlternateNames()) {
                    if (itemString.contains(s)) {
                        isFoodIem = true;
                        break;
                    }
                    if (isFoodIem) {
                        break;
                    }
                }
            }
            if (!isFoodIem) {
                return new Item(itemString);
            }
            String[] items = itemString.split(" ");
            var foodItem = new FoodItem();
            String amt;
            for (String s : items) {
                if (!(amt = findNumberInString(s)).isEmpty()) {
                    foodItem.amount = amt;
                    foodItem.measurement = getMeasurementFromString(s);
                }

            }
            return foodItem;
        }


        private static Measurement getMeasurementFromString(String s) {
            return Arrays.stream(Measurement.values())
                    .map(Measurement::getAlternateNames)
                    .flatMap(Collection::stream)
                    .filter(s::contains)
                    .map(MeasurementUtils::fromString)
                    .findFirst()
                    .orElse(null);
        }
    }
}
