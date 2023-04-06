package com.example.grocerylist.web;

import java.util.List;

public class ItemDTO {

    private String name;
    private List<String> amounts;

    public ItemDTO(String name, List<String> amounts) {
        this.name = name;
        this.amounts = amounts;
    }

    public ItemDTO() {
    }

    public String getName() {
        return name;
    }

    public List<String> getAmounts() {
        return amounts;
    }
}
