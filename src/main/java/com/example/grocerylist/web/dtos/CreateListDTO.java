package com.example.grocerylist.web.dtos;

import jakarta.validation.constraints.Size;

public class CreateListDTO {

    @Size(max = 50, min = 1)
    private String name;

    public CreateListDTO(String name) {
        this.name = name;
    }

    public CreateListDTO() {
    }

    public String getName() {
        return name;
    }
}
