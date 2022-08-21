package com.example.shoppinglist.item;

import com.example.shoppinglist.errors.IncompatibleTypes;
import org.springframework.stereotype.Component;

@Component
public class MeasurementComparator {

    Measurement addMeasurementToItem(Item item, Measurement newMeasurement) {
        var staticItem = item.staticItem;

        var itemMetricMeasurement = convertToMetric(item.measurement);
        var newMetricMeasurement = convertMeasurementUnit(convertToMetric(newMeasurement), itemMetricMeasurement.getUnit(), staticItem);
        var totalAmountInMetric = itemMetricMeasurement.getAmount() + newMetricMeasurement.getAmount();

        return new Measurement(totalAmountInMetric, itemMetricMeasurement.getUnit());
    }

    Measurement convertToMetric(Measurement measurement) {
        if (Unit.NONE.equals(measurement.getUnit())) return measurement;
        var amount = measurement.getUnit().convertToMetric(measurement.getAmount());
        var unit = measurement.getUnit().toMetric();
        return new Measurement(amount, unit);
    }

    Measurement convertMeasurementUnit(Measurement measurement, Unit targetUnit, StaticItem staticItem) {
        var sourceUnit = measurement.getUnit();

        if (targetUnit.getType().equals(sourceUnit.getType())) {
            return scaleFrom(measurement, targetUnit);
        }

        if (staticItem == null) {
            throw new IncompatibleTypes();
        }

        if (Unit.NONE.equals(sourceUnit) || Unit.NONE.equals(targetUnit)) {
            return combineBaseUnitWithNoUnit(measurement, targetUnit, staticItem);
        }

        var metricMeasurementAmount = convertToMetric(measurement).getAmount();

        return Unit.UnitType.VOLUME.equals(sourceUnit.getType()) ?
                scaleFrom(new Measurement(staticItem.density * metricMeasurementAmount, Unit.GRAM), targetUnit) :
                scaleFrom(new Measurement(metricMeasurementAmount / staticItem.density, Unit.LITER), targetUnit);

    }

    Measurement combineBaseUnitWithNoUnit(Measurement measurement, Unit targetUnit, StaticItem staticItem) {
        var convertingUnit = Unit.UnitType.VOLUME.equals(measurement.getUnit().getType()) ||
                Unit.UnitType.VOLUME.equals(targetUnit.getType()) ?
                Unit.UnitType.VOLUME :
                Unit.UnitType.WEIGHT;

        double conversionRatio = staticItem.ratioGramsToNone != 0 ?
                Unit.UnitType.WEIGHT.equals(convertingUnit) ?
                        staticItem.ratioGramsToNone : staticItem.ratioGramsToNone / staticItem.density :
                Unit.UnitType.VOLUME.equals(convertingUnit) ?
                        staticItem.ratioLitersToNone : staticItem.ratioLitersToNone * staticItem.density;

        conversionRatio = Unit.NONE.equals(targetUnit) ? 1 / conversionRatio : conversionRatio;

        return new Measurement(measurement.getAmount() * conversionRatio, targetUnit);
    }

    Measurement getClosestWholeAmount(Measurement measurement, StaticItem staticItem) {
        if (staticItem == null) {
            return measurement;
        }

        var staticMeasurement = staticItem.defaultMeasurement;
        var convertedMeasurement = convertMeasurementUnit(measurement, staticMeasurement.unit, staticItem);
        var currentMeasurement = scaleFrom(convertedMeasurement, staticMeasurement.getUnit());

        if (currentMeasurement.compareTo(staticMeasurement) < 0) {
            currentMeasurement.setAmount(staticMeasurement.getAmount());
        }

        return getLargestUnit(currentMeasurement);
    }

    Measurement scaleFrom(Measurement sourceMeasurement, Unit targetUnit) {
        if (!sourceMeasurement.getUnit().getType().equals(targetUnit.getType())) {
            throw new IncompatibleTypes();
        }

        var convertingUnit = sourceMeasurement.getUnit();
        var convertingAmount = sourceMeasurement.getAmount();
        double amount;

        if (!convertingUnit.getStandard().equals(targetUnit.getStandard())) {
            amount = convertingAmount * convertingUnit.toBase() * convertingUnit.toOtherStandard() / targetUnit.toBase();
        } else {
            amount = convertingAmount * convertingUnit.toBase() / targetUnit.toBase();
        }

        return new Measurement(amount, targetUnit);
    }

    Measurement getLargestUnit(Measurement currentMeasurement) {
        if (Unit.NONE.equals(currentMeasurement.unit) && currentMeasurement.amount % 1 != 0) {
            int wholeAmount = (int) currentMeasurement.getAmount() + 1;
            return new Measurement(wholeAmount, currentMeasurement.unit);
        }

        var nextUnit = currentMeasurement.getUnit().getNextLargestUnit();
        if (nextUnit == null) {
            return currentMeasurement;
        }

        var newMeasurement = scaleFrom(currentMeasurement, nextUnit);

        if (newMeasurement.getAmount() < 1) {
            return currentMeasurement;
        }

        return getLargestUnit(newMeasurement);
    }
}
