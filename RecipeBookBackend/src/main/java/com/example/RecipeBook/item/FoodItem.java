package com.example.RecipeBook.item;

import com.example.RecipeBook.errors.ItemsCannotBeCombined;
import com.example.RecipeBook.measurement.Measurement;
import org.apache.commons.lang3.tuple.Pair;

import javax.persistence.*;

import static com.example.RecipeBook.NumberUtils.getNumberFromString;

@Entity
@Table(name = "Item")
@DiscriminatorValue("F")
public class FoodItem extends Item {
    @Enumerated(EnumType.STRING)
    Measurement measurement;

    public static FoodItem ConvertStringToItem(String s) {
        var item = new FoodItem();

        return item;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }


    //MEASUREMENT TYPE -> VOLUME WEIGHT
    //MEASUREMENT STANDARD -> IMPERIAL METRIC
    @Override
    void combineWithItem(Item newItem) {
        if (!(newItem instanceof FoodItem)) {
            throw new ItemsCannotBeCombined();
        }
        var foodItem = (FoodItem) newItem;
        if (this.name.equals(foodItem.name) || this.shareAliasWithItem(foodItem)) {
            Pair<String, Measurement> updatedMeasurement = combineWithFoodItem(foodItem);
            this.amount = updatedMeasurement.getLeft();
            this.measurement = updatedMeasurement.getRight();
        } else {
            throw new ItemsCannotBeCombined();
        }
    }

    private Pair<String, Measurement> combineWithFoodItem(FoodItem foodItem) {
        String left;
        Measurement right = this.measurement;
        var amount = getNumberFromString(this.amount);
        var amountTwo = getNumberFromString(foodItem.amount);
        if (this.measurement.equals(foodItem.measurement)) {
            return Pair.of(String.valueOf(amount + amountTwo), right);
        }
        var tempMeasurement = this.measurement.toStandardBase();
        var tempAmt = this.measurement.scaleToStandardBase(amount) + foodItem.measurement.scaleToStandardBase(amountTwo);
        if (tempMeasurement.equals(this.staticItem.defaultMeasurement)) {
            left = this.measurement.scaleFrom(right, tempAmt);
            return Pair.of(left, tempMeasurement);
        }
        return this.getLargestWholeMeasurement(tempAmt, tempMeasurement);
    }

    private Pair<String, Measurement> getLargestWholeMeasurement(double tempAmt, Measurement measurement) {

        return null;
    }

    private boolean shareAliasWithItem(FoodItem foodItem) {
        for (String alias : foodItem.staticItem.aliases) {
            if (this.staticItem.aliases.contains(alias)) {
                return true;
            }
        }
        return false;
    }
}
