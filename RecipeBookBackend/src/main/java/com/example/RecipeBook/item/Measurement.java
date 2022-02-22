package com.example.RecipeBook.item;

import com.example.RecipeBook.utils.NumberUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;
import java.util.regex.Pattern;

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

    @Converter(autoApply = true)
    public static class MeasurementConverter implements AttributeConverter<Measurement, String> {
        public static final Pattern pattern = Pattern.compile("([0-9]+\\.*/*[0-9]*)([a-z]*)");

        @Override
        public String convertToDatabaseColumn(Measurement measurement) {
            if (measurement == null) {
                return null;
            }
            return measurement.toString();
        }

        @Override
        public Measurement convertToEntityAttribute(String measurementString) {
            if (measurementString == null) {
                return null;
            }
            var matcher = pattern.matcher(measurementString);
            var measurement = new Measurement();

            if (!matcher.find()) {
                return measurement;
            }

            double amount = NumberUtils.getNumberFromString(matcher.group(1));
            Unit unit = Unit.getUnitFromString(matcher.group(2));
            measurement.setAmount(amount);
            measurement.setUnit(unit);

            return measurement;
        }
    }


}
