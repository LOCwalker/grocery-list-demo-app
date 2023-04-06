package com.example.grocerylist.web.dtos;


import java.util.List;

public class GroceryListDTO {

    private long id;
    private String name;
    private List<ItemDTO> items;

    public GroceryListDTO(long id, String name, List<ItemDTO> items) {
        this.id = id;
        this.name = name;
        this.items = items;
    }

    public GroceryListDTO() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ItemDTO> getItems() {
        return items;
    }
}
