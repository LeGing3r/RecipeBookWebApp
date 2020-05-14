package com.example.RecipeBook.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    private int qty = 1;

    private boolean needed = true;

    @ManyToOne
    private TodoItemsDto todoItemsDto;

    public void increaseQty(int amt){
        this.qty += amt;
    }
}
