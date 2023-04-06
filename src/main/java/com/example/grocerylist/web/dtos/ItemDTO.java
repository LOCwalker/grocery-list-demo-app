package com.example.grocerylist.web.dtos;

import java.util.List;

public class ItemDTO {

    private String name;
    private List<String> amounts;
    private boolean bought;

    public ItemDTO(String name, List<String> amounts, boolean bought) {
        this.name = name;
        this.amounts = amounts;
        this.bought = bought;
    }

    public ItemDTO() {
    }

    public String getName() {
        return name;
    }

    public List<String> getAmounts() {
        return amounts;
    }

    public boolean isBought() {
        return bought;
    }
}
