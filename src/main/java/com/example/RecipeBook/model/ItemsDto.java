package com.example.RecipeBook.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class ItemsDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "itemsDto", fetch = FetchType.EAGER)
    private List<Item> items = new ArrayList<>();

}
