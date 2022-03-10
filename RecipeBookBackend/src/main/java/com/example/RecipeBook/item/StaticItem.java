package com.example.RecipeBook.item;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class StaticItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Double ratioGramsToNone = 0.0;
    Double ratioLitersToNone = 0.0;
    double density;// g/L
    @Convert(converter = Measurement.MeasurementConverter.class)
    Measurement defaultMeasurement;
    @ElementCollection(targetClass = String.class)
    final Set<String> aliases = new HashSet<>();
    String name;
    @OneToMany(mappedBy = "staticItem")
    private final List<Item> items = new ArrayList<>();
}
