package com.example.grocerylist.web;


public class GroceryListDTO {

    private long id;
    private String name;

    public GroceryListDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GroceryListDTO() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
