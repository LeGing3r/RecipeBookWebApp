package com.example.shoppinglist.item;

import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    final Set<String> aliases = new HashSet<>();
    @OneToMany(mappedBy = "staticItem")
    private final List<Item> items = new ArrayList<>();
}
