package com.example.RecipeBook.item;

import com.example.RecipeBook.measurement.Measurement;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class StaticItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Enumerated(EnumType.STRING)
    Measurement defaultMeasurement;
    double defaultAmount;
    double amountInGrams;
    double amountInLiters;
    String stringValue;
    @ElementCollection(targetClass = String.class)
    final Set<String> aliases = new HashSet<>();
    String name;
}
