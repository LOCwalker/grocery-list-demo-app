package com.example.grocerylist.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("grocery_lists")
public class GroceryListEntity {

    @Id
    @Column("id")
    private long id;
    @Column("name")
    private String name;

    public GroceryListEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
