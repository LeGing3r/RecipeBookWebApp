package com.example.RecipeBook.measurement;

import java.util.Objects;

public class Measurement implements Comparable<Measurement> {
    double amount;
    Unit unit;

    public Measurement() {
    }

    public Measurement(double amount, Unit unit) {
        this.amount = amount;
        this.unit = unit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public int compareTo(Measurement otherMeasurement) {
        var thisType = this.unit.getType();
        var otherType = otherMeasurement.getUnit().getType();

        if (!thisType.equals(otherType)) {
            return thisType.compareToType(otherType);
        }
        var difference = (this.unit.convertToMetric(this.amount) - otherMeasurement.unit.convertToMetric(otherMeasurement.amount));
        return difference > 0 ? 1 : difference == 0 ? 0 : -1;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Measurement that = (Measurement) o;
        return amount == that.amount && unit == that.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, unit);
    }

    @Override
    public String toString() {
        return amount + "" + unit;
    }


}
